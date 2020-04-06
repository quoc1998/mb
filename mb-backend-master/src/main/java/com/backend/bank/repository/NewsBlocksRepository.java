package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.NewsBlocks;

public interface NewsBlocksRepository extends JpaRepository<NewsBlocks, Integer>, JpaSpecificationExecutor<NewsBlocks> {

}