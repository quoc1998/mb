package com.backend.bank.dto.response.setting;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
@Data
public class EmailTemplateRoponseDto {
    private Integer id;
    private String name;
    private String subject;
    private String content;
    private String code;
    private Boolean active;
    private String emailCc;

}
