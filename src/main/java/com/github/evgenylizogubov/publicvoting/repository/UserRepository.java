package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailIgnoreCase(String email);
    
    @Transactional
    int removeById(int id);
    
    boolean existsByEmail(String email);
}
