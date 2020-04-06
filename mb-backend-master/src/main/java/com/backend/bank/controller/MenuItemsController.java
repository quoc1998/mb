package com.backend.bank.controller;

import com.backend.bank.dto.request.MenuItemsRequestDto;
import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.service.MenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{local}/api/menu/{menuId}/menuItem")
public class MenuItemsController {

    @Autowired
    MenuItemsService menuItemsService;

    @Secured({"ROLE_GET MENUITEM", "ROLE_XEM MENUITEM"})
    @GetMapping
    public List<MenuItemsReponseDto> findAll(@PathVariable("local") String local, @PathVariable("menuId") Integer menuId) {
        return menuItemsService.findAllMenuItem(local, menuId);
    }

    @Secured({"ROLE_GET MENUITEM", "ROLE_XEM MENUITEM"})
    @GetMapping("/{menuItemId}")
    public List<MenuItemsReponseDto> findById(@PathVariable("menuId") Integer menuId, @PathVariable("local") String local,
                                              @PathVariable("menuItemId") Integer menuItemId) {
        return menuItemsService.findById(menuId, local, menuItemId);
    }


    @Secured({"ROLE_CREATE MENUITEM", "ROLE_TẠO MENUITEM"})
    @PostMapping
    public MenuItemsReponseDto addMenuItems(@RequestBody MenuItemsRequestDto menuItemsRequestDto,
                                            @PathVariable("local") String local, @PathVariable("menuId") Integer menuId) {
        return menuItemsService.addMenuItems(menuItemsRequestDto, local, menuId);
    }

    @Secured({"ROLE_EDIT MENUITEM", "ROLE_CHỈNH SỬA MENUITEM"})
    @PutMapping("/{menuItemId}")
    public MenuItemsReponseDto editMenuItems(@RequestBody MenuItemsRequestDto menuItemsRequestDto,
                                             @PathVariable("local") String local,
                                             @PathVariable("menuItemId") Integer menuItemId,
                                             @PathVariable("menuId") Integer menuId) {


        return menuItemsService.editMenuItems(menuId, menuItemId, menuItemsRequestDto, local);
    }


    @Secured({"ROLE_DELETE MENUITEM", "ROLE_XÓA MENUITEM"})
    @DeleteMapping("/{menuItemId}")
    public int deleteMenuItems(@PathVariable("menuItemId") int id, @PathVariable("menuId") Integer menuId) {
        return menuItemsService.deleteMenuItems(id, menuId);
    }

    @GetMapping("/sort/{id}")
    public List<MenuItemsReponseDto> sortMenuItem(@PathVariable("id") int id, @PathVariable("locale") String locale) {
        return menuItemsService.sortMenuItem(id, locale);
    }

    @Secured({"ROLE_UPDATE POSITION MENUITEM", "ROLE_CHỈNH SỬA VỊ TRÍ MENUITEM"})
    @PutMapping("/update_position/{idParent}/{id}")
    public Integer updatePosition(@PathVariable("menuId") Integer menuId, @PathVariable("idParent") Integer idParent, @PathVariable("id") int id,
                                  @RequestParam(value = "position", required = true) int position,
                                  @PathVariable("local") String locale) {
        return menuItemsService.updatePosition(idParent, id, position, locale);
    }

}

