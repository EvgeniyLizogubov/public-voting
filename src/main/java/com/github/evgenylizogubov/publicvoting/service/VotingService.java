package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.voting.VotingDto;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.voting.VotingDtoToVotingMapper;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Theme;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VotingService {
    private final VotingRepository votingRepository;
    private final VotingDtoToVotingMapper votingDtoToVotingMapper;
    private final ThemeService themeService;
    private final IsDayOffService isDayOffService;
    
    @Value("${voting.stage.duration}")
    private int votingStageDuration;
    
    public VotingDto get(int id) {
        Optional<Voting> voting = votingRepository.findById(id);
        return voting.map(votingDtoToVotingMapper::toDto).orElse(null);
    }
    
    public List<VotingDto> getAllActive() {
        List<Voting> activeVoting = votingRepository.findAllByIsActiveIsTrueOrderByStartGettingSuggestionsDateAsc();
        return votingDtoToVotingMapper.toDtoList(activeVoting);
    }
    
    public VotingDto getCurrentActive() {
        List<Voting> voting = votingRepository.findCurrentActive(LocalDate.now());
        Voting currentActiveVoting = null;
        
        for (Voting v : voting) {
            if (!LocalDate.now().isBefore(v.getStartGettingSuggestionsDate())) {
                if (!LocalDate.now().isAfter(v.getStartGettingSuggestionsDate().plusDays(votingStageDuration))) {
                    currentActiveVoting = v;
                    break;
                } else {
                    finishVoting(v);
                }
            }
        }
        
        return votingDtoToVotingMapper.toDto(currentActiveVoting);
    }
    
    public VotingDto getLastFinished() {
        Voting lastFinished = votingRepository.findFirstByIsActiveIsFalseOrderByStartGettingVotesDateDesc();
        return votingDtoToVotingMapper.toDto(lastFinished);
    }
    
    @Transactional
    public VotingDto create() {
        Theme theme = themeService.getFirstUnused();
        if (theme == null) {
            return null;
        }
        
        LocalDate startGettingSuggestionsDate = getStartGettingSuggestionsDate();
        LocalDate startGettingVotesDate = getStartGettingVotesDate(startGettingSuggestionsDate);
        
        Voting voting = new Voting();
        voting.setTheme(theme);
        voting.setStartGettingSuggestionsDate(startGettingSuggestionsDate);
        voting.setStartGettingVotesDate(startGettingVotesDate);
        voting.setIsActive(true);
        
        Voting created = votingRepository.save(voting);
        return votingDtoToVotingMapper.toDto(created);
    }
    
    public List<VotingDto> getAll() {
        List<Voting> voting = votingRepository.findAll();
        return votingDtoToVotingMapper.toDtoList(voting);
    }
    
    @Transactional
    public VotingDto update(VotingDto votingDto, int id) {
        if (!votingRepository.existsById(id)) {
            throw new NotFoundException("Theme with id=" + id + " not found");
        }
        
        votingDto.setId(id);
        Voting updated = votingRepository.save(votingDtoToVotingMapper.toEntity(votingDto));
        return votingDtoToVotingMapper.toDto(updated);
    }
    
    public int delete(int id) {
        return votingRepository.removeById(id);
    }
    
    private LocalDate getStartGettingSuggestionsDate() {
        Voting lastActiveVoting = votingRepository.findFirstByIsActiveIsTrueOrderByStartGettingSuggestionsDateDesc();
        
        TemporalAdjuster temporalAdjuster = TemporalAdjusters.firstDayOfNextMonth();
        LocalDate date =
                lastActiveVoting == null ?
                LocalDate.now() :
                lastActiveVoting.getStartGettingSuggestionsDate().with(temporalAdjuster);
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
                .filter(entry -> entry.getValue() == Collections.max(elementsCounts.values()))
                .map(Map.Entry::getKey)
                .toList();
        
        if (mostFrequentSuggestions.size() == 1) {
            votingToFinish.setWinningSuggestion(mostFrequentSuggestions.getFirst());
        }
        
        votingToFinish.setIsActive(false);
    }
}
