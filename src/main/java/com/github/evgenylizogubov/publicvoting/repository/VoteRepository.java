package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Vote;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    int countAllByVotingIdAndChosenSuggestionId(int votingId, int suggestionId);
    
    boolean existsByVotingAndUser(Voting voting, User user);
}
