package com.github.evgenylizogubov.publicvoting.repository;

import com.github.evgenylizogubov.publicvoting.model.Certificate;
import com.github.evgenylizogubov.publicvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Integer> {
    List<Certificate> findAllByOwner(User owner);
    
    boolean existsByOwnerAndYear(User owner, int year);
}
