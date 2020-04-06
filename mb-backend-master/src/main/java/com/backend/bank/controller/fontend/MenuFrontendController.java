package com.backend.bank.controller.fontend;


import com.backend.bank.dto.response.menu.MenuFullResponseDto;
import com.backend.bank.dto.response.menu.MenuResponseDto;
import com.backend.bank.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/fe/menu")
public class MenuFrontendController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<MenuResponseDto> getAll(@PathVariable("locale") String locale) {
        return menuService.getAll(locale);
    }


    @GetMapping("/position/{position}")
    public MenuFullResponseDto findByPosition(@PathVariable("locale") String locale, @PathVariable("position") String position) {
        return menuService.findByPosition(locale, position);
    }

    @GetMapping("/{id}")
    public MenuFullResponseDto getMenuById(@PathVariable("locale") String locale, @PathVariable("id") int id) {
        return menuService.getById(id, locale);
    }


}
