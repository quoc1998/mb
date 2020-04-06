package com.backend.bank.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FAQRequest {
    @NotNull(message = "groupFAQId not null")
    private Integer groupFAQId;
    @NotNull(message = "question not null")
    private String question;
    @NotNull(message = "answer not null")
    private String answer;
    @NotNull(message = "isActive not null")
    private Boolean isActive;
}
