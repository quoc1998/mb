package com.backend.bank.dto.request;

import com.backend.bank.model.MenuTranslations;
import lombok.Data;

import java.util.Calendar;

import java.util.List;


@Data
public class MenuRequestDto {
    private int status;
    private String name;
    private String title;
    private String position;
}
