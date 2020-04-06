package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.PageBlockTranslations;

public interface PageBlockTranslationsRepository extends JpaRepository<PageBlockTranslations, Integer>, JpaSpecificationExecutor<PageBlockTranslations> {

}