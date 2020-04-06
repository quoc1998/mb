package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExchangeRateDetailRequestDto implements Serializable {
    private String currency;
    private Double buy_cash;
    private Double buy_transfer;
    private Double sell;
    private Double change_USD;
}
