package com.backend.bank.repository;

import com.backend.bank.model.ExchangeRate;
import com.backend.bank.model.ExchangeRateDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExchangeRateDetailRepository extends JpaRepository<ExchangeRateDetail, Integer> {
    List<ExchangeRateDetail> findByExchangeRate(ExchangeRate exchangeRate);

    Optional<ExchangeRateDetail> findByCurrencyAndExchangeRate(String currency, ExchangeRate exchangeRate);
}
