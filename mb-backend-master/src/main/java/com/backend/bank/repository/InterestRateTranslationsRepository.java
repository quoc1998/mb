package com.backend.bank.repository;

import com.backend.bank.model.InterestRate;
import com.backend.bank.model.InterestRateTranslations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRateTranslationsRepository extends JpaRepository<InterestRateTranslations, Integer> {
    Optional<InterestRateTranslations> findByLocaleAndInterestRate(String locale, InterestRate interestRate);

    List<InterestRateTranslations> findByInterestRate(InterestRate interestRate);
}
