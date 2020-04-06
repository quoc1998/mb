package com.backend.bank.repository;

import com.backend.bank.model.InvestorsTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestorsTranslationRepository extends JpaRepository<InvestorsTranslation, Integer> {
}
