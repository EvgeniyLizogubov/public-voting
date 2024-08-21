package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.error.NotFoundException;
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
    
    public Suggestion get(int id) {
        return suggestionRepository.findById(id).orElse(null);
    }
    
    public List<Suggestion> getAllByVoting(Voting voting) {
        return suggestionRepository.findAllByVoting(voting);
    }
    
    @Transactional
    public Suggestion create(Suggestion suggestion) {
        if (suggestionRepository.existsByVotingAndUser(suggestion.getVoting(), suggestion.getUser())) {
            throw new IllegalRequestDataException("The suggestion for voting has already been sent");
        }
        
        if (suggestionRepository.existsByDescriptionAndVoting(suggestion.getDescription(), suggestion.getVoting())) {
            throw new IllegalRequestDataException("Suggestion with description \"" + suggestion.getDescription() +
                    "\" and voting \"" + suggestion.getVoting() + "\" already exists");
        }
        
        return suggestionRepository.save(suggestion);
    }
    
    @Transactional
    public Suggestion update(Suggestion suggestion, int id) {
        if (!suggestionRepository.existsById(id)) {
            throw new NotFoundException("Suggestion with id=" + id + " not found");
        }
        
        Optional<Suggestion> checkedSuggestion = suggestionRepository.findByDescriptionAndVoting(suggestion.getDescription(),
                suggestion.getVoting());
        if (checkedSuggestion.isPresent() && checkedSuggestion.get().getId() != id) {
            throw new IllegalRequestDataException("Suggestion with description \"" + suggestion.getDescription() +
                    "\" and voting \"" + suggestion.getVoting() + "\" already exists");
        }
        
        suggestion.setId(id);
        return suggestionRepository.save(suggestion);
    }
    
    public int delete(int id) {
        return suggestionRepository.removeById(id);
    }
}
