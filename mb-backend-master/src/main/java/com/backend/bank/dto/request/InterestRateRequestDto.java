package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class InterestRateRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private int term;
    private float interest_rate;
    private String description;
}
