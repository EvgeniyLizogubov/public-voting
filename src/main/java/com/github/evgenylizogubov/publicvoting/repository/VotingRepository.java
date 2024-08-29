package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Integer> {
    @Transactional
    int removeById(Integer id);
    
    List<Voting> findAllByIsActiveIsTrueOrderByStartGettingSuggestionsDateAsc();
    
    @Query("SELECT v FROM Voting v WHERE v.isActive = true AND v.startGettingSuggestionsDate <= :date")
    List<Voting> findCurrentActive(LocalDate date);
    
    Voting findFirstByIsActiveIsTrueOrderByStartGettingSuggestionsDateDesc();
    
    Voting findFirstByIsActiveIsFalseOrderByStartGettingVotesDateDesc();
}
