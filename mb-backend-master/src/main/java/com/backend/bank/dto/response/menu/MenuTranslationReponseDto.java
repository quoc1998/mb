package com.backend.bank.dto.response.menu;

import lombok.Data;

@Data
public class MenuTranslationReponseDto {
    private Integer id;
    private Integer menu_id;
    private String locale;
    private String name;
}
