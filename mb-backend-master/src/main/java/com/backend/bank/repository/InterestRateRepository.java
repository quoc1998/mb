package com.backend.bank.repository;

import com.backend.bank.model.InterestRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Integer> {
    @Query("SELECT b FROM InterestRate b")
    Page<InterestRate> getAllBy(Pageable pageable);
}
