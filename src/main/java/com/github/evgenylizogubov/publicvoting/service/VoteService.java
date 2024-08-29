package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    
    public Vote get(int id) {
        return voteRepository.findById(id).orElse(null);
    }
    
    public int getCountAllByVotingIdAndChosenSuggestionId(int votingId, int suggestionId) {
        return voteRepository.countAllByVotingIdAndChosenSuggestionId(votingId, suggestionId);
    }
    
    @Transactional
    public void create(Vote vote) {
        if (voteRepository.existsByVotingAndUser(vote.getVoting(), vote.getUser())) {
            throw new IllegalRequestDataException("The vote for voting has already been sent");
        }
        
        voteRepository.save(vote);
    }
}
