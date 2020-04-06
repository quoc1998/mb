package com.backend.bank.repository;

import com.backend.bank.model.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.PageBlocks;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageBlocksRepository extends JpaRepository<PageBlocks, Integer>, JpaSpecificationExecutor<PageBlocks> {
    List<Pages> findAllById(List<Integer> ids);
}