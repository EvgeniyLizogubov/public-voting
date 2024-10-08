package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    @Transactional
    int removeById(int id);
    
    Theme findByDescription(String description);
    
    boolean existsByDescription(String description);
    
    Theme getFirstByIsUsedFalse();
    
    @Modifying
    @Transactional
    @Query("UPDATE Theme t SET t.isUsed = :usage")
    void setUsageForAll(boolean usage);
}
