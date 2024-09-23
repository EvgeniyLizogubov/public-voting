package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.Suggestion;
import com.github.evgenylizogubov.publicvoting.model.User;
import com.github.evgenylizogubov.publicvoting.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Integer> {
    @Transactional
    int removeById(int id);
    
    List<Suggestion> findAllByVotingId(int votingId);
    
    Suggestion findByDescriptionAndVoting(String description, Voting voting);
    
    boolean existsByDescriptionAndVoting(String description, Voting voting);
    
    boolean existsByVotingAndUser(Voting voting, User user);
}
