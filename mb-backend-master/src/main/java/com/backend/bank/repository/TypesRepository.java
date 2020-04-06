package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Types;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypesRepository extends JpaRepository<Types, Integer>, JpaSpecificationExecutor<Types> {
    @Query("from Types t where t.id = ?1")
    Optional<Types> findById(Integer id);

    @Query("from Types t where t.names = ?1")
    Optional<Types> findByNames(String id);

    List<Types> findAll();
}