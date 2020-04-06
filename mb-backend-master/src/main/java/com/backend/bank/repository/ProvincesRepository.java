package com.backend.bank.repository;

import com.backend.bank.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvincesRepository extends JpaRepository<Provinces, Integer> {
}
