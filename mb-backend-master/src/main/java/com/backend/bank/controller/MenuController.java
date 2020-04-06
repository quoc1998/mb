package com.backend.bank.controller;


import com.backend.bank.dto.request.MenuRequestDto;
import com.backend.bank.dto.response.menu.MenuFullResponseDto;
import com.backend.bank.dto.response.menu.MenuResponseDto;
import com.backend.bank.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("{locale}/api/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @Secured({"ROLE_GET MENU", "ROLE_XEM MENU"})
    @GetMapping
    public List<MenuResponseDto> getAll(@PathVariable("locale") String locale) {
        return menuService.getAll(locale);
    }

    @Secured({"ROLE_GET MENU", "ROLE_XEM MENU"})
    @GetMapping("/position")
    public List<MenuResponseDto> findAllByPosition(@PathVariable("locale") String locale) {
        return menuService.findAllByPosition(locale);
    }

    @Secured({"ROLE_GET MENU", "ROLE_XEM MENU"})
    @GetMapping("/position/{position}")
    public MenuFullResponseDto findByPosition(@PathVariable("locale") String locale, @PathVariable("position") String position) {
        return menuService.findByPosition(locale, position);
    }

    @Secured({"ROLE_GET MENU", "ROLE_XEM MENU"})
    @GetMapping("/{id}")
    public MenuFullResponseDto getMenuById(@PathVariable("locale") String locale, @PathVariable("id") int id) {
        return menuService.getById(id, locale);
    }

    @Secured({"ROLE_CREATE MENU", "ROLE_TẠO MENU"})
    @PostMapping
    public MenuResponseDto addMenu(@PathVariable("locale") String locale, @RequestBody MenuRequestDto menuRequestDto) {
        return menuService.addMenu(locale, menuRequestDto);
    }

    @Secured({"ROLE_EDIT MENU", "ROLE_CHỈNH SỬA MENU"})
    @PutMapping("/{id}")
    public MenuResponseDto editMenu(@PathVariable("locale") String locale, @PathVariable("id") int id, @RequestBody MenuRequestDto menuRequestDto) {
        return menuService.editMenu(id, locale, menuRequestDto);
    }

    @Secured({"ROLE_DELETE MENU", "ROLE_XÓA MENU"})
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable("locale") String locale, @PathVariable("id") int id) {
        menuService.deleteMenu(locale, id);
    }

    @Secured({"ROLE_DELETE MENU", "ROLE_XÓA MENU"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListMenu(@RequestBody List<Integer> ids) {
        return menuService.deleteIds(ids);
    }

}
