package com.backend.bank.repository;

import com.backend.bank.model.FAQTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQTranslationRepository extends JpaRepository<FAQTranslation, Integer> {
}
