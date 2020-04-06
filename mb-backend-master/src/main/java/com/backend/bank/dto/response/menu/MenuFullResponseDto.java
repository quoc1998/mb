package com.backend.bank.dto.response.menu;

import lombok.Data;

import java.util.List;

@Data
public class MenuFullResponseDto {
    private Integer id;
    private int status;
    private String name;
    private String title;
    private String position;
    private List<MenuItemsReponseDto> menuItems;
}
