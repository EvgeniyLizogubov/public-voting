package com.github.evgenylizogubov.publicvoting.service;

import com.github.evgenylizogubov.publicvoting.controller.dto.user.UserDto;
import com.github.evgenylizogubov.publicvoting.controller.dto.vote.VoteDto;
import com.github.evgenylizogubov.publicvoting.error.IllegalRequestDataException;
import com.github.evgenylizogubov.publicvoting.mapper.user.UserDtoToUserMapper;
import com.github.evgenylizogubov.publicvoting.mapper.vote.VoteDtoToVoteMapper;
import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import com.github.evgenylizogubov.publicvoting.repository.VoteRepository;
import com.github.evgenylizogubov.publicvoting.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;
    private final VotingRepository votingRepository;
    private final VoteDtoToVoteMapper voteDtoToVoteMapper;
    private final UserDtoToUserMapper userDtoToUserMapper;
    
    public VoteDto get(int id, UserDto userDto) {
        Vote vote = voteRepository.findByIdAndUser(id, userDtoToUserMapper.toEntity(userDto));
        return voteDtoToVoteMapper.toDto(vote);
    }
    
    @Transactional
    public void create(int chosenSuggestionId, UserDto userDto) {
        Voting activeVoting = votingRepository.findByIsActiveIsTrue();
        User user = userDtoToUserMapper.toEntity(userDto);
        
        if (voteRepository.existsByVotingAndUser(activeVoting, user)) {
            throw new IllegalRequestDataException("The vote for voting has already been sent");
        }
        
        Suggestion chosenSuggestion = activeVoting.getSuggestions().stream()
                .filter(suggestion -> suggestion.getId() == chosenSuggestionId)
                .findFirst()
                .orElseThrow(() -> new IllegalRequestDataException("You can't vote for this suggestion"));
        
        Vote vote = new Vote(activeVoting, chosenSuggestion, user);
        
        voteRepository.save(vote);
    }
}
