package com.backend.bank.repository;

import com.backend.bank.model.News;
import com.backend.bank.model.NewsTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.NewsBlockTranslations;

import java.util.List;

public interface NewsBlockTranslationsRepository extends JpaRepository<NewsBlockTranslations, Integer>, JpaSpecificationExecutor<NewsBlockTranslations> {
}