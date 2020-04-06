package com.backend.bank.converters.bases.converter.menu;

import com.backend.bank.common.Constants;
import com.backend.bank.dto.request.MenuItemsRequestDto;
import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.CategoryTranslations;
import com.backend.bank.model.Menu;
import com.backend.bank.model.MenuItemTranslations;
import com.backend.bank.model.MenuItems;
import com.backend.bank.repository.MenuItemTranslationRepository;
import com.backend.bank.repository.MenuItemsRepository;
import com.backend.bank.repository.MenuRepository;
import com.backend.bank.repository.PagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MenuItemConverter {

    @Autowired
    MenuItemsRepository menuItemsRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuItemTranslationRepository menuItemTranslationRepository;

    @Autowired
    PagesRepository pagesRepository;

    public MenuItemsReponseDto converterEntity(MenuItems menuItems,String local){
        MenuItemsReponseDto menuItemsReponseDto = new MenuItemsReponseDto();
        menuItemsReponseDto.setMenuId(menuItems.getMenu().getId());
        menuItemsReponseDto.setCategoryId(menuItems.getCategoryId());
        menuItemsReponseDto.setCategoryNewId(menuItems.getCategoryNewId());
        menuItemsReponseDto.setType(menuItems.getType());
        menuItemsReponseDto.setTarget(menuItems.getTargetId());
        menuItemsReponseDto.setRoot(menuItems.getRoot());
        menuItemsReponseDto.setSlugPages(menuItems.getSlugPages());
        menuItemsReponseDto.setActive(menuItems.getActive());
        menuItemsReponseDto.setFluid(menuItems.getFluid());
        menuItemsReponseDto.setId(menuItems.getId());
        if (menuItems.getMenuItems() != null){
            menuItemsReponseDto.setParentId(menuItems.getMenuItems().getId());
        }
        menuItemsReponseDto.setPosition(menuItems.getPosition());
        menuItemsReponseDto.setSlug(menuItems.getSlug());
        menuItemsReponseDto.setIcon(menuItems.getIcon());
        menuItemsReponseDto.setUrl(menuItems.getUrl());

        MenuItemTranslations menuItemTranslations = menuItemTranslationRepository.findByMenuItemsAndLocale(menuItems,local).orElse(null);

        if (null != menuItemTranslations){
            menuItemsReponseDto.setName(menuItemTranslations.getName());
            menuItemsReponseDto.setDescription(menuItemTranslations.getDescription());
        }else{
            MenuItemTranslations menuItemTranslation = new MenuItemTranslations();
            menuItemTranslation.setName("");
            menuItemTranslation.setDescription("");
            menuItemTranslation.setLocale(local);
            menuItemTranslation.setMenuItems(menuItems);
            menuItemTranslationRepository.save(menuItemTranslation);
            menuItemsReponseDto.setName("");
            menuItemsReponseDto.setDescription("");
        }
        return menuItemsReponseDto;
    }

    public List<MenuItemsReponseDto> converterListMenuItem(List<MenuItems> menuItemsList,String local){
        List<MenuItemsReponseDto> menuItemsReponseDtos = new ArrayList<>();
        for (MenuItems entity: menuItemsList) {
            MenuItemsReponseDto menuItemsReponseDto1 = this.converterEntity(entity,local);
            menuItemsReponseDtos.add(menuItemsReponseDto1);
        }
        return menuItemsReponseDtos;
    };

    public MenuItems converterMenuItemDto(String local, MenuItemsRequestDto menuItemsRequestDto, Integer idMenu){
        MenuItems menuItems = new MenuItems();
        menuItems.setActive(menuItemsRequestDto.getActive());
        menuItems.setFluid(menuItemsRequestDto.getFluid());
        menuItems.setRoot(menuItemsRequestDto.getRoot());
        menuItems.setPosition(menuItemsRequestDto.getPosition());
        menuItems.setCategoryId(menuItemsRequestDto.getCategoryId());
        menuItems.setType(menuItemsRequestDto.getType());
        menuItems.setTargetId(menuItemsRequestDto.getTargetId());
        menuItems.setRoot(menuItemsRequestDto.getRoot());
        menuItems.setSlug(Constants.toSlug(menuItemsRequestDto.getName()));
        menuItems.setSlugPages(menuItemsRequestDto.getSlugPages());
        menuItems.setIcon(menuItemsRequestDto.getIcon());
        menuItems.setUrl(menuItemsRequestDto.getUrl());
        menuItems.setCategoryNewId(menuItemsRequestDto.getCategoryNewId());
        if (null != menuItemsRequestDto.getParentId()){
            MenuItems menuItem = menuItemsRepository.findById(menuItemsRequestDto.getParentId()).get();
            menuItems.setMenuItems(menuItem);
        }
        Menu menu = menuRepository.findById(idMenu).orElse(null);
        if (menu == null){
            throw new NotFoundException("Menu not found");
        }
        List<MenuItemTranslations> menuItemTranslationsList = new ArrayList<>();
        MenuItemTranslations menuItemTranslations = new MenuItemTranslations();
        menuItemTranslations.setName(menuItemsRequestDto.getName());
        menuItemTranslations.setDescription(menuItemsRequestDto.getDescription());
        menuItemTranslations.setLocale(local);
        menuItemTranslations.setMenuItems(menuItems);
        menuItemTranslationsList.add(menuItemTranslations);
        menuItems.setMenuItemTranslations(menuItemTranslationsList);
        menuItems.setMenu(menu);
        Date date = new Date();
        menuItems.setCreateAt(date);
        return menuItems;
    }

    public MenuItemsReponseDto converterMenuMiddle(MenuItems menuItems, String local){
        MenuItemsReponseDto menuItemsReponseDto = new MenuItemsReponseDto();
        menuItemsReponseDto.setPosition(menuItems.getPosition());
        menuItemsReponseDto.setSlug(menuItems.getSlug());
        menuItemsReponseDto.setSlugPages(menuItems.getSlugPages());
        Integer location = checkTranslation(menuItems.getId(), menuItems.getMenuItemTranslations(), local);
        if (location != -1){
            menuItemsReponseDto.setDescription(menuItems.getMenuItemTranslations().get(location).getDescription());
            menuItemsReponseDto.setName(menuItems.getMenuItemTranslations().get(location).getName());
        }else {
            MenuItemTranslations menuItemTranslation = new MenuItemTranslations();
            menuItemTranslation.setName("");
            menuItemTranslation.setDescription("");
            menuItemTranslation.setLocale(local);
            menuItemTranslation.setMenuItems(menuItems);
            menuItemTranslationRepository.save(menuItemTranslation);
            menuItemsReponseDto.setName("");
            menuItemsReponseDto.setDescription("");
        }
        menuItemsReponseDto.setIcon(menuItems.getIcon());
        menuItemsReponseDto.setUrl(menuItems.getUrl());
        return menuItemsReponseDto;
    }

    public List<MenuItemsReponseDto> converterListMenuItemOfMiddle(List<MenuItems> menuItemsList,String local){
        List<MenuItemsReponseDto> menuItemsReponseDtos = new ArrayList<>();
        for (MenuItems entity: menuItemsList) {
            MenuItemsReponseDto menuItems = this.converterMenuMiddle(entity, local);
            menuItemsReponseDtos.add(menuItems);
        }
        return menuItemsReponseDtos;
    }

    public Integer checkTranslation(Integer id, List<MenuItemTranslations> menuItemTranslations, String local){
        Integer size = menuItemTranslations.size();
        if (size.equals(0)){
            throw new NotFoundException("Menu Middle not found translation: "+id);
        }
        for (Integer i = 0; i< size; i++ ){
            if (local.equals(menuItemTranslations.get(i).getLocale())){
                return i;
            }
        }
        return -1;
    }
}
