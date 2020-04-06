package com.backend.bank.dto.request;

import lombok.Data;

@Data
public class MenuTranslationRequestDto {
    private Integer id;
    private Integer menu_id;
    private String locale;
    private String name;
}
