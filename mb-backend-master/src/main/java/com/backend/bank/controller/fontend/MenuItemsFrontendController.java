package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.service.MenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("{local}/api/fe/menu/{menuId}/menuItem")
public class MenuItemsFrontendController {

    @Autowired
    MenuItemsService menuItemsService;

    @GetMapping
    public List<MenuItemsReponseDto> findAll(@PathVariable("local") String local, @PathVariable("menuId") Integer menuId) {
        return menuItemsService.findAllMenuItem(local, menuId);
    }

    @GetMapping("/{menuItemId}")
    public List<MenuItemsReponseDto> findById(@PathVariable("menuId") Integer menuId, @PathVariable("local") String local,
                                              @PathVariable("menuItemId") Integer menuItemId) {
        return menuItemsService.findById(menuId, local, menuItemId);
    }



}

