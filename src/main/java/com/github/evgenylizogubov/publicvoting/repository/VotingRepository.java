package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.Voting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VotingRepository extends JpaRepository<Voting, Integer> {
    @Transactional
    int removeById(Integer id);
    
    Voting findByIsActiveIsTrue();
    
    boolean existsById(int id);
}
