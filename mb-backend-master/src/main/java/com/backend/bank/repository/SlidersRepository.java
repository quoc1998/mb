package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Sliders;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SlidersRepository extends JpaRepository<Sliders, Integer>, JpaSpecificationExecutor<Sliders> {
    @Transactional
    @Modifying
    @Query("delete from Sliders i where i.id in ?1")
    void deleteAllById(List<Integer> ids);
}