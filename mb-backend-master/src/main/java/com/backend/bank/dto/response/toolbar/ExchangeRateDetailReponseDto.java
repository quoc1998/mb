package com.backend.bank.dto.response.toolbar;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExchangeRateDetailReponseDto implements Serializable {
    private Integer id;
    private String currency;
    private Double buy_cash;
    private Double buy_transfer;
    private Double sell;
    private Double change_USD;
}
