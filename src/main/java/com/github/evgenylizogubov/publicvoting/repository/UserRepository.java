package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailIgnoreCase(String email);
    
    @Transactional
    int removeById(int id);
    
    boolean existsByEmail(String email);
    
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.points = :points")
    void setPointsForAll(int points);
    
    @Query("SELECT u FROM User u WHERE u.points = (SELECT MAX(u2.points) FROM User u2)")
    List<User> getUserByMaxPoints();
}
