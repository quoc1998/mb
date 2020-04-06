package com.backend.bank.dto.response.investors;

import com.backend.bank.dto.response.investors.InvestorsResponse;
import lombok.Data;

import java.util.List;

@Data
public class PaginationInvestors {
    private List<InvestorsResponse> investors;
    private Integer size;
}
