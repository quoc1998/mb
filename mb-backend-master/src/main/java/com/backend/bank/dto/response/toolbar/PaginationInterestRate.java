package com.backend.bank.dto.response.toolbar;

import lombok.Data;

import java.util.List;
@Data
public class PaginationInterestRate {
    private List<InterestRateReponseDto> interestRates;
    private Integer size;
}
