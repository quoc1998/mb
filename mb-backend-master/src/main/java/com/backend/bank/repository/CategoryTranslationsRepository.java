package com.backend.bank.repository;

import com.backend.bank.model.Category;
import com.backend.bank.model.CategoryTranslations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryTranslationsRepository extends JpaRepository<CategoryTranslations, Integer> {
    List<CategoryTranslations> findAllByCategory_IdAndLocale(int categoryId, String locale);

    Optional<CategoryTranslations> findByCategoryAndLocale(Category category, String local);

    List<CategoryTranslations> findByCategory(Category category);
}
