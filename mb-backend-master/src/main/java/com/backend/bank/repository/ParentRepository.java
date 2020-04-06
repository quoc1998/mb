package com.backend.bank.repository;

import com.backend.bank.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<Parent, Integer> {
    List<Parent> findAll();
}
