package com.backend.bank.dto.response.setting;

import lombok.Data;

import java.util.List;

@Data
public class PaginationEmailTemplate {
    private List<EmailTemplateRoponseDto> emailTemplate;
    private Integer size;
}
