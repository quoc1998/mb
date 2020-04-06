package com.backend.bank.converters.bases.converter;


import com.backend.bank.converters.bases.converter.menu.MenuItemConverter;
import com.backend.bank.dto.request.MenuRequestDto;

import com.backend.bank.dto.response.menu.MenuFullResponseDto;
import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.dto.response.menu.MenuMiddle;
import com.backend.bank.dto.response.menu.MenuResponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.CategoryTranslations;
import com.backend.bank.model.Menu;
import com.backend.bank.model.MenuTranslations;
import com.backend.bank.repository.MenuRepository;
import com.backend.bank.repository.MenuTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@Component
public class MenuConverter {
    @Autowired
    private MenuTranslationRepository menuTranslationRepository;

    @Autowired
    private MenuItemConverter menuItemConverter;

    @Autowired
    private MenuRepository menuRepository;

    private Calendar calendar = Calendar.getInstance();

    public List<MenuResponseDto> convertListMenuToDto(List<Menu> menus, String local) {
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
        for (Menu menuItem : menus) {
            MenuResponseDto menuResponseDto = this.convertMenuToDto(menuItem, local);
            menuResponseDtoList.add(menuResponseDto);

        }
        return menuResponseDtoList;

    }

    public Menu convertMenuDtoToEntity(MenuRequestDto menuRequestDto, String local) {
        Menu menu = new Menu();
        menu.setStatus(menuRequestDto.getStatus());
        menu.setCreateAt(calendar);
        if (menuRequestDto.getPosition() != "") {
            menu.setPosition(menuRequestDto.getPosition());
            if (!menuRequestDto.getPosition().equalsIgnoreCase("middle")) {
                Optional<Menu> menuOptional = menuRepository.findByPosition(menuRequestDto.getPosition());
                if (menuOptional.isPresent()) {
                    menuOptional.get().setPosition("");
                    menuRepository.save(menuOptional.get());
                }
            }

        } else {
            menu.setPosition("");
        }
        List<MenuTranslations> menuTranslationsList = new ArrayList<>();
        MenuTranslations menuTranslations = new MenuTranslations();
        menuTranslations.setLocale(local);
        menuTranslations.setName(menuRequestDto.getName());
        menuTranslations.setTitle(menuRequestDto.getTitle());
        menuTranslations.setMenu(menu);
        menuTranslationsList.add(menuTranslations);
        menu.setMenuTranslations(menuTranslationsList);
        return menu;
    }

    public MenuResponseDto convertMenuToDto(Menu menu, String local) {
        MenuResponseDto menuResponseDto = new MenuResponseDto();
        menuResponseDto.setId(menu.getId());
        menuResponseDto.setStatus(menu.getStatus());
        menuResponseDto.setPosition(menu.getPosition());
        MenuTranslations menuTranslations = menuTranslationRepository.findByMenuAndLocale(menu, local).orElse(null);
        if (menuTranslations != null) {
            menuResponseDto.setName(menuTranslations.getName());
            menuResponseDto.setTitle(menuTranslations.getTitle());
        } else {
            MenuTranslations menuTranslation = new MenuTranslations();
            menuTranslation.setLocale(local);
            menuTranslation.setName(menu.getMenuTranslations().get(0).getName());
            menuTranslation.setTitle(menu.getMenuTranslations().get(0).getTitle());
            menuTranslation.setMenu(menu);
            menuTranslationRepository.save(menuTranslation);
            menuResponseDto.setName(menu.getMenuTranslations().get(0).getName());
            menuResponseDto.setTitle(menu.getMenuTranslations().get(0).getTitle());
        }
        return menuResponseDto;
    }

    public MenuFullResponseDto convertMenuFullToDto(Menu menu, String local) {
        MenuFullResponseDto menuResponseDto = new MenuFullResponseDto();
        menuResponseDto.setId(menu.getId());
        menuResponseDto.setMenuItems(menuItemConverter.converterListMenuItem(menu.getMenuItems(), local));
        menuResponseDto.setStatus(menu.getStatus());
        menuResponseDto.setPosition(menu.getPosition());
        MenuTranslations menuTranslations = menuTranslationRepository.findByMenuAndLocale(menu, local).orElse(null);
        if (menuTranslations != null) {
            menuResponseDto.setName(menuTranslations.getName());
            menuResponseDto.setTitle(menuTranslations.getTitle());
        } else {
            MenuTranslations menuTranslation = new MenuTranslations();
            menuTranslation.setLocale(local);
            menuTranslation.setName(menu.getMenuTranslations().get(0).getName());
            menuTranslation.setTitle(menu.getMenuTranslations().get(0).getTitle());
            menuTranslation.setMenu(menu);
            menuTranslationRepository.save(menuTranslation);
            menuResponseDto.setName(menu.getMenuTranslations().get(0).getName());
            menuResponseDto.setTitle(menu.getMenuTranslations().get(0).getTitle());
        }
        return menuResponseDto;
    }

    public MenuMiddle converterMenuMiddle(Menu menu, String local) {
        MenuMiddle menuMiddle = new MenuMiddle();
        menuMiddle.setId(menu.getId());
        Integer location = checkTranslation(menu.getMenuTranslations(), local);
        if (location != -1){
            menuMiddle.setTitle(menu.getMenuTranslations().get(location).getTitle());
        }
        List<MenuItemsReponseDto> menuItems = menuItemConverter.converterListMenuItemOfMiddle(menu.getMenuItems(), local);
        menuMiddle.setMenuItems(menuItems);
        return menuMiddle;
    }
    public Integer checkTranslation(List<MenuTranslations> menuTranslations, String local) {
        Integer size = menuTranslations.size();
        if (size.equals(0)) {
            throw new NotFoundException("MenuMiddle not found translation");
        }
        for (Integer i = 0; i < size; i++) {
            if (local.equals(menuTranslations.get(i).getLocale())) {
                return i;
            }
        }
        return -1;
    }
}
