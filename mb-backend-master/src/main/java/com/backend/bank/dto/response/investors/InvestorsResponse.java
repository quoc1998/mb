package com.backend.bank.dto.response.investors;

import lombok.Data;

import java.util.Date;

@Data
public class InvestorsResponse {
    private Integer id;
    private Boolean isActive;
    private String name;
    private String urlFile;
    private String nameFile;
    private String description;
    private String urlVideo;
    private Date createdAt;
    private Integer typeInvestors;
    private Integer detailTypeInvestors;
}
