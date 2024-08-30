package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.suggestion.SuggestionDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
import com.github.evgenylizogubov.publicvoting.mapper.suggestion.SuggestionDtoToSuggestionMapper;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final SuggestionDtoToSuggestionMapper suggestionDtoToSuggestionMapper;
    
    public SuggestionDto get(int id) {
        Optional<Suggestion> suggestion = suggestionRepository.findById(id);
        return suggestion.map(suggestionDtoToSuggestionMapper::toDto).orElse(null);
    }
    
    public List<SuggestionDto> getAllByVoting(Voting voting) {
        List<Suggestion> suggestions = suggestionRepository.findAllByVoting(voting);
        return suggestionDtoToSuggestionMapper.toDtoList(suggestions);
    }
    
    @Transactional
    public SuggestionDto create(SuggestionDto suggestionDto) {
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
    public SuggestionDto update(SuggestionDto suggestionDto, int id) {
        if (!suggestionRepository.existsById(id)) {
            throw new NotFoundException("Suggestion with id=" + id + " not found");
        }
        
        Optional<Suggestion> checkedSuggestion = suggestionRepository.findByDescriptionAndVoting(suggestionDto.getDescription(),
                suggestionDto.getVoting());
        if (checkedSuggestion.isPresent() && checkedSuggestion.get().getId() != id) {
            throw new IllegalRequestDataException("Suggestion with description \"" + suggestionDto.getDescription() +
                    "\" and voting \"" + suggestionDto.getVoting() + "\" already exists");
        }
        
        suggestionDto.setId(id);
        Suggestion updated = suggestionRepository.save(suggestionDtoToSuggestionMapper.toEntity(suggestionDto));
        return suggestionDtoToSuggestionMapper.toDto(updated);
    }
    
    public int delete(int id) {
        return suggestionRepository.removeById(id);
    }
}
