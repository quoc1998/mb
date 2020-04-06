package com.backend.bank.dto.response.toolbar;

import lombok.Data;

import java.util.List;
@Data
public class PaginationExchangeRate {
    private List<ExchangeRateReponseDto> exchangeRates;
    private Integer size;
}
