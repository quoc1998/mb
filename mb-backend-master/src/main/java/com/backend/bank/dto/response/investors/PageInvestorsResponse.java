package com.backend.bank.dto.response.investors;

import lombok.Data;

import java.util.List;
@Data
public class PageInvestorsResponse {
    private Integer year;
    private List<InvestorsResponse> investors;
}
