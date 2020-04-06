package com.backend.bank.dto.response.toolbar;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Data
public class ExchangeRateReponseDto implements Serializable {
    private Integer id;
    private Calendar date_update;
    private Calendar created_at;
    private List<ExchangeRateDetailReponseDto> exchangeRateDetail;
}
