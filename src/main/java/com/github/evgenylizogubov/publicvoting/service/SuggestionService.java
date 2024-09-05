package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.suggestion.SuggestionDtoToSuggestionMapper;
import com.github.evgenylizogubov.publicvoting.mapper.user.UserDtoToUserMapper;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.repository.SuggestionRepository;
import com.github.evgenylizogubov.publicvoting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final VotingRepository votingRepository;
    private final SuggestionDtoToSuggestionMapper suggestionDtoToSuggestionMapper;
    private final UserDtoToUserMapper userDtoToUserMapper;
    
    public SuggestionDto get(int id) {
        Optional<Suggestion> suggestion = suggestionRepository.findById(id);
        return suggestion.map(suggestionDtoToSuggestionMapper::toDto).orElse(null);
    }
    
    public List<SuggestionDto> getAllByVotingId(int votingId) {
        if (!votingRepository.existsById(votingId)) {
            throw new IllegalRequestDataException("Voting with id=" + votingId + " not found");
        }
        
        List<Suggestion> suggestions = suggestionRepository.findAllByVotingId(votingId);
        return suggestionDtoToSuggestionMapper.toDtoList(suggestions);
    }
    
    @Transactional
    public SuggestionDto create(SuggestionDto suggestionDto, UserDto userDto) {
        Voting activeVoting = votingRepository.findByIsActiveIsTrue();
        suggestionDto.setVoting(activeVoting);
        suggestionDto.setUser(userDtoToUserMapper.toEntity(userDto));
        
        if (suggestionRepository.existsByVotingAndUser(suggestionDto.getVoting(), suggestionDto.getUser())) {
            throw new IllegalRequestDataException("The suggestion for voting has already been sent");
        }
        
        if (suggestionRepository.existsByDescriptionAndVoting(suggestionDto.getDescription(), suggestionDto.getVoting())) {
            throw new IllegalRequestDataException("Suggestion with description \"" + suggestionDto.getDescription() +
                    "\" and voting \"" + suggestionDto.getVoting() + "\" already exists");
        }
        
        Suggestion created = suggestionRepository.save(suggestionDtoToSuggestionMapper.toEntity(suggestionDto));
        return suggestionDtoToSuggestionMapper.toDto(created);
    }
    
    @Transactional
    public SuggestionDto update(SuggestionDto suggestionDto, int id, UserDto userDto) {
        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suggestion with id=" + id + " not found"));
        
        if (!suggestion.getUser().equals(userDtoToUserMapper.toEntity(userDto))) {
            throw new IllegalRequestDataException("Only the creator of the suggestion can change it");
        }
        
        if (!LocalDate.now().isBefore(suggestion.getVoting().getStartGettingVotesDate())) {
            throw new IllegalRequestDataException("Changing the suggestion is no longer available");
        }
        
        Suggestion checkedSuggestion = suggestionRepository
                .findByDescriptionAndVoting(suggestionDto.getDescription(), suggestionDto.getVoting());
        if (checkedSuggestion != null && checkedSuggestion.getId() != id) {
            throw new IllegalRequestDataException("Suggestion with description \"" + suggestionDto.getDescription() +
                    "\" and voting \"" + suggestionDto.getVoting() + "\" already exists");
        }
        
        suggestion.setDescription(suggestionDto.getDescription());
        return suggestionDtoToSuggestionMapper.toDto(suggestion);
    }
    
    public int delete(int id, UserDto userDto) {
        Optional<Suggestion> suggestion = suggestionRepository.findById(id);
        
        if (suggestion.isEmpty()) {
            return 0;
        }
        
        if (!suggestion.get().getUser().equals(userDtoToUserMapper.toEntity(userDto))) {
            throw new IllegalRequestDataException("Only the creator of the suggestion can delete it");
        }
        
        if (!LocalDate.now().isBefore(suggestion.get().getVoting().getStartGettingVotesDate())) {
            throw new IllegalRequestDataException("Deleting the suggestion is no longer available");
        }
        
        return suggestionRepository.removeById(id);
    }
}
