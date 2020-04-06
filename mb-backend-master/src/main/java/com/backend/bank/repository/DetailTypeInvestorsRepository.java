package com.backend.bank.repository;

import com.backend.bank.model.DetailTypeInvestors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailTypeInvestorsRepository extends JpaRepository<DetailTypeInvestors, Integer> {
    Optional<DetailTypeInvestors> findById(Integer id);
    List<DetailTypeInvestors> findAllByTypeInvestorsId(Integer idType);
}
