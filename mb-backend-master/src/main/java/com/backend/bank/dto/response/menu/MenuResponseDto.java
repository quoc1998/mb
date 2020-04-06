package com.backend.bank.dto.response.menu;

import com.backend.bank.model.MenuTranslations;
import lombok.Data;

import java.util.Calendar;

import java.util.List;


@Data
public class MenuResponseDto {
    private Integer id;
    private int status;
    private String name;
    private String title;
    private String position;
}
