package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.mapper.vote.VoteDtoToVoteMapper;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.repository.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final VoteDtoToVoteMapper voteDtoToVoteMapper;
    
    public VoteDto get(int id) {
        Optional<Vote> vote = voteRepository.findById(id);
        return vote.map(voteDtoToVoteMapper::toDto).orElse(null);
    }
    
    public int getCountAllByVotingIdAndChosenSuggestionId(int votingId, int suggestionId) {
        return voteRepository.countAllByVotingIdAndChosenSuggestionId(votingId, suggestionId);
    }
    
    @Transactional
    public void create(VoteDto voteDto) {
        if (voteRepository.existsByVotingAndUser(voteDto.getVoting(), voteDto.getUser())) {
            throw new IllegalRequestDataException("The vote for voting has already been sent");
        }
        
        voteRepository.save(voteDtoToVoteMapper.toEntity(voteDto));
    }
}
