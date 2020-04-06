package com.backend.bank.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class FAQResponse {
    private Integer id;
    private Integer groupFAQId;
    private String question;
    private String answer;
    private Date createAt;
}
