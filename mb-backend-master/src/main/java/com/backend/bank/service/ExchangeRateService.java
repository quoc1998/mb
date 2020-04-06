package com.backend.bank.service;

import com.backend.bank.dto.request.ExchangeRateRequestDto;
import com.backend.bank.dto.response.toolbar.ExchangeRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationExchangeRate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface ExchangeRateService {
    List<ExchangeRateReponseDto> getAll(String locale);

    PaginationExchangeRate findAllPagin(String locale, Integer page, Integer number);

    PaginationExchangeRate findRangeDate(String locale, Integer page, Integer number, String dateStart, String dateStop);

    ExchangeRateReponseDto addExchangeRate(String locale, ExchangeRateRequestDto exchangeRateRequestDto);

    ExchangeRateReponseDto getExchangeRate(String date);

    ExchangeRateReponseDto getExchangeRateNew();

    ExchangeRateReponseDto getExchangeRate(String locale, Integer id);

    ExchangeRateReponseDto editExchangeRate(String locale, Integer id, ExchangeRateRequestDto exchangeRateRequestDto);

    void deleteExchangeRate(String locale, Integer id);

    ExchangeRateReponseDto filterDateUpdate(String locale, ExchangeRateRequestDto exchangeRateRequestDto);

    void importExchangeRate(String local);
}
