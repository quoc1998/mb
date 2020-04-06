package com.backend.bank.dto.response.toolbar;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class InterestRateReponseDto implements Serializable {
    private Integer id;
    private String locale;
    private int term;
    private float interest_rate;
    private String description;
    private Date created_at;
    private Date updated_at;
}
