package com.backend.bank.dto.response.menu;

import lombok.Data;

import java.util.List;
@Data
public class MenuMiddle {
    private Integer id;
    private String title;
    private List<MenuItemsReponseDto> menuItems;
}
