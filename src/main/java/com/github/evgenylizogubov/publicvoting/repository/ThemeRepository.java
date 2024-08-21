package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    @Transactional
    int removeById(int id);

    Optional<Theme> findByDescription(String description);

    boolean existsByDescription(String description);
}
