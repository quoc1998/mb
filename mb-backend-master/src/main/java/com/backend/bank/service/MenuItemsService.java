package com.backend.bank.service;

import com.backend.bank.dto.request.MenuItemsRequestDto;
import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuItemsService {
    List<MenuItemsReponseDto> findAllMenuItem(String local, Integer id);
    MenuItemsReponseDto addMenuItems(MenuItemsRequestDto menuItemsRequestDto,String local, Integer idMenu);
    List<MenuItemsReponseDto> findById(Integer id,String local, Integer menuItemId);
    MenuItemsReponseDto editMenuItems(Integer menuId, Integer menuItemId, MenuItemsRequestDto menuItemsRequestDto,String local);
    int  deleteMenuItems(Integer id, Integer menu);
    List<MenuItemsReponseDto> findAllByLocal(String local);
    Integer updatePosition (Integer parentId, int id, int position, String local);
    List<MenuItemsReponseDto> sortMenuItem(Integer parentId, String local);
}
