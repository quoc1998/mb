package com.backend.bank.service;

import com.backend.bank.dto.request.InterestRateRequestDto;
import com.backend.bank.dto.response.toolbar.InterestRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationInterestRate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InterestRateService {
    List<InterestRateReponseDto> getAll(String locale);

    PaginationInterestRate findAllPagin(String locale, Integer page, Integer number);

    InterestRateReponseDto addInterestRate(String locale, InterestRateRequestDto interestRateRequestDto);

    InterestRateReponseDto getInterestRate(String locale, Integer id);

    InterestRateReponseDto editInterestRate(String locale, Integer id, InterestRateRequestDto interestRateRequestDto);

    void deleteInterestRate(String locale, Integer id);
}
