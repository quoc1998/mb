package com.backend.bank.dto.request;

import lombok.Data;

import java.sql.Date;
@Data
public class EmailTemplateRequestDto {
    private String name;
    private String code;
    private Boolean active;
    private String emailCc;
    private String subject;
    private String content;

}
