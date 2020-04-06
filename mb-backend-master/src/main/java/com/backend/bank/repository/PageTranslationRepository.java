package com.backend.bank.repository;

import com.backend.bank.model.PageTranslations;
import com.backend.bank.model.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface PageTranslationRepository extends JpaRepository<PageTranslations, Integer> {
    Optional<PageTranslations> findByPagesAndLocale(Pages pages, String locale);
    List<PageTranslations> findByPages(Pages pages);
    Optional<PageTranslations> findByNameAndLocale(String name,String local);
    @Query("from PageTranslations p where p.slug = ?1 and p.locale = ?2")
    List<PageTranslations> findBySlugAndLocale(String name,String local);
    Optional<PageTranslations> findByName(String name);
}
