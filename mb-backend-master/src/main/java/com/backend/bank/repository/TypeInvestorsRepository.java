package com.backend.bank.repository;

import com.backend.bank.model.TypeInvestors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeInvestorsRepository extends JpaRepository<TypeInvestors, Integer> {
    Optional<TypeInvestors> findById(Integer id);
}
