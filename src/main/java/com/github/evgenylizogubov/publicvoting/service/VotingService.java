package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingDto;
import com.github.evgenylizogubov.publicvoting.mapper.voting.VotingDtoToVotingMapper;
import com.github.evgenylizogubov.publicvoting.model.Certificate;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.repository.CertificateRepository;
import com.github.evgenylizogubov.publicvoting.repository.UserRepository;
import com.github.evgenylizogubov.publicvoting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;
    private final VotingDtoToVotingMapper votingDtoToVotingMapper;
    private final ThemeService themeService;
    private final IsDayOffService isDayOffService;
    private final CertificateService certificateService;
    
    @Value("${voting.stage.duration}")
    private int votingStageDuration;
    
    public VotingDto get(int id) {
        Optional<Voting> voting = votingRepository.findById(id);
        return voting.map(votingDtoToVotingMapper::toDto).orElse(null);
    }
    
    public VotingDto getActive() {
        Voting active = votingRepository.findByIsActiveIsTrue();
        return active == null ? null : votingDtoToVotingMapper.toDto(active);
    }
    
    @Transactional
    public VotingDto create() {
        Theme theme = themeService.getFirstUnused();
        if (theme == null) {
            return null;
        }
        
        theme.setIsUsed(true);
        
        Voting activeVoting = votingRepository.findByIsActiveIsTrue();
        
        LocalDate startGettingSuggestionsDate = getStartGettingSuggestionsDate(activeVoting);
        LocalDate startGettingVotesDate = getStartGettingVotesDate(startGettingSuggestionsDate);
        
        if (activeVoting != null) {
            finishVoting(activeVoting);
        }
        
        Voting newVoting = new Voting();
        newVoting.setTheme(theme);
        newVoting.setStartGettingSuggestionsDate(startGettingSuggestionsDate);
        newVoting.setStartGettingVotesDate(startGettingVotesDate);
        
        Voting created = votingRepository.save(newVoting);
        return votingDtoToVotingMapper.toDto(created);
    }
    
    public List<VotingDto> getAll() {
        List<Voting> voting = votingRepository.findAll();
        return votingDtoToVotingMapper.toDtoList(voting);
    }
    
    public int delete(int id) {
        return votingRepository.removeById(id);
    }
    
    private LocalDate getStartGettingSuggestionsDate(Voting previousVoting) {
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.firstDayOfNextMonth();
        LocalDate date =
                previousVoting == null ?
                        LocalDate.now() :
                        previousVoting.getStartGettingSuggestionsDate().with(temporalAdjuster);
        int halfMonth = date.lengthOfMonth() / 2;
        
        if (date.getDayOfMonth() > halfMonth) {
            date = date.with(temporalAdjuster);
        }
        
        IsDayOffServiceResponse isDayOffServiceResponse = null;
        int countDays = 0;
        
        do {
            isDayOffServiceResponse = isDayOffService.getDateInfo(date.plusDays(countDays++));
        } while (!isDayOffServiceResponse.isWorkingDay());
        
        return isDayOffServiceResponse.getDate();
    }
    
    private LocalDate getStartGettingVotesDate(LocalDate startGettingSuggestionsDate) {
        IsDayOffServiceResponse isDayOffServiceResponse = null;
        int countDays = votingStageDuration;
        
        do {
            isDayOffServiceResponse = isDayOffService.getDateInfo(startGettingSuggestionsDate.plusDays(countDays++));
        } while (!isDayOffServiceResponse.isWorkingDay());
        
        return isDayOffServiceResponse.getDate();
    }
    
    private void finishVoting(Voting votingToFinish) {
        Set<Vote> votes = votingToFinish.getVotes();
        Map<Suggestion, Long> elementsCounts = votes.stream()
                .collect(Collectors.groupingBy(Vote::getChosenSuggestion, Collectors.counting()));
        
        List<Suggestion> mostFrequentSuggestions = elementsCounts.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), Collections.max(elementsCounts.values())))
                .map(Map.Entry::getKey)
                .toList();
        
        if (mostFrequentSuggestions.size() == 1) {
            User winner = mostFrequentSuggestions.getFirst().getUser();
            winner.setPoints(winner.getPoints() + 1);
            
            votingToFinish.setWinningSuggestion(mostFrequentSuggestions.getFirst());
        }
        
        if (votingToFinish.getStartGettingVotesDate().getMonth() == Month.SEPTEMBER) {
            int currentYear = votingToFinish.getStartGettingVotesDate().getYear();
            awardWinnerAtEndOfYear(currentYear);
        }
        
        votingToFinish.setIsActive(false);
    }
    
    private void awardWinnerAtEndOfYear(int year) {
        List<User> winners = userRepository.getUserByMaxPoints();
        if (winners.size() == 1) {
            certificateService.sendByEmail(winners.getFirst(), year);
            certificateRepository.save(new Certificate(year, winners.getFirst()));
        }
        
        userRepository.setPointsForAll(0);
    }
}
