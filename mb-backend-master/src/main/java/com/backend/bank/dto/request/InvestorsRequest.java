package com.backend.bank.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class InvestorsRequest {
    private Boolean isActive;
    private String name;
    private String urlFile;
    private String nameFile;
    private String description;
    private String urlVideo;
    private Integer typeInvestors;
    private Date createdAt;
    private Integer detailTypeInvestors;
}
