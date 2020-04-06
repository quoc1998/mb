package com.backend.bank.repository;

import com.backend.bank.model.Parent;
import com.backend.bank.model.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {
    List<Target> findAll();
}
