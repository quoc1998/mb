package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.BlockValueTranslations;

public interface BlockValueTranslationsRepository extends JpaRepository<BlockValueTranslations, Integer>, JpaSpecificationExecutor<BlockValueTranslations> {

}