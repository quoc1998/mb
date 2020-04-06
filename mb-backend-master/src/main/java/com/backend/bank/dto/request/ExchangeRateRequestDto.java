package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Data
public class ExchangeRateRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;
    private Calendar date_update;
    private Calendar createdAt;
    private List<ExchangeRateDetailRequestDto> exchangeRateDetail;
}
