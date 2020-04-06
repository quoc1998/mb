package com.backend.bank.repository;

import com.backend.bank.model.News;
import com.backend.bank.model.NewsTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsTranslationRepository  extends JpaRepository<NewsTranslation, Integer> {
   Optional<NewsTranslation> findByNewsAndLocale(News news , String local);

   List<NewsTranslation> findByNews(News news);
   List<NewsTranslation> findByUrlAndLocale(String url , String local);
}
