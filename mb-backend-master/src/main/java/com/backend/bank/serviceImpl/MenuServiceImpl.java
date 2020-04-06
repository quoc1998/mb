package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;

import com.backend.bank.converters.bases.converter.MenuConverter;
import com.backend.bank.dto.request.MenuRequestDto;
import com.backend.bank.dto.response.menu.MenuFullResponseDto;
import com.backend.bank.dto.response.menu.MenuResponseDto;
import com.backend.bank.exception.FoundException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Menu;
import com.backend.bank.model.MenuTranslations;

import com.backend.bank.repository.MenuRepository;
import com.backend.bank.repository.MenuTranslationRepository;
import com.backend.bank.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuTranslationRepository menuTranslationRepository;

    @Autowired
    private MenuConverter menuConverter;

    @Override
    public List<MenuResponseDto> getAll(String locale) {
        Constants.checkLocal(locale);
        List<Menu> menus = menuRepository.findAllByDeletedAt(null);
        List<MenuResponseDto> menuDtoList = menuConverter.convertListMenuToDto(menus, locale);
        return menuDtoList;
    }

    @Override
    public List<MenuResponseDto> findAllByPosition(String locale) {
        List<Menu> menus = menuRepository.findAllByPositionAndDeletedAt("middle", null);
        List<MenuResponseDto> menuDtoList = menuConverter.convertListMenuToDto(menus, locale);
        return menuDtoList;
    }

    @Override
    public MenuFullResponseDto findByPosition(String locale, String position) {
        Constants.checkPositionOfMenu(position);
        Menu menu = menuRepository.findByPosition(position).orElse(null);
        if (menu == null){
            throw new NotFoundException("Not Found Menu");
        }
        if (menu.getStatus()==0){
            throw new NotFoundException("Menu Not Active");
        }
        return menuConverter.convertMenuFullToDto(menu, locale);
    }


    @Override
    public MenuFullResponseDto getById(int id, String locale) {
        Constants.checkLocal(locale);
        Menu menu = menuRepository.findByDeletedAtAndId(null, id);
        if (menu == null) {
            throw new NotFoundException("Menu not found");
        }
        return menuConverter.convertMenuFullToDto(menu, locale);

    }

    @Override
    public MenuResponseDto addMenu(String locale, MenuRequestDto menuRequestDto) {
        Constants.checkLocal(locale);
        Constants.checkPositionOfMenu(menuRequestDto.getPosition());
        MenuResponseDto menuResponseDto;
        try {
            List<Menu> menuList = menuRepository.findAllByPosition(menuRequestDto.getPosition());
            if (menuList.size() > 0 && !menuRequestDto.getPosition().equalsIgnoreCase("middle")){
                throw new FoundException("Position exist");
            }else {
                Menu menu = menuConverter.convertMenuDtoToEntity(menuRequestDto, locale);
                menuRepository.save(menu);
                menuResponseDto = menuConverter.convertMenuToDto(menu, locale);
            }

        } catch (Exception e) {

            throw new FoundException("Position exist");
        }

        return menuResponseDto;
    }

    @Override
    public MenuResponseDto editMenu(int id, String locale, MenuRequestDto menuRequestDto) {
        Constants.checkPositionOfMenu(menuRequestDto.getPosition());
        Constants.checkLocal(locale);
        Menu menu = menuRepository.findById(id).orElse(null);
        if (menu == null) {
            throw new NotFoundException("Menu not found");
        }
        try {
            menu.setStatus(menuRequestDto.getStatus());
            if (menuRequestDto.getPosition() != "") {
                menu.setPosition(menuRequestDto.getPosition());
                if (!menuRequestDto.getPosition().equalsIgnoreCase("middle")) {
                    Optional<Menu> menuOptional = menuRepository.findByPosition(menuRequestDto.getPosition());
                    if (menuOptional.isPresent()&& menuOptional.get().getId() != id) {
                        throw new FoundException("Position exist");
                    }
                }
            } else {
                menu.setPosition("");
            }
            MenuTranslations menuTranslations = menuTranslationRepository.findByMenuAndLocale(menu, locale).orElse(null);
            if (menuTranslations != null) {
                List<MenuTranslations> menuTranslationsList = new ArrayList<>();
                menuTranslations.setName(menuRequestDto.getName());
                menuTranslations.setTitle(menuRequestDto.getTitle()
                );
                menuTranslationsList.add(menuTranslations);
                menu.setMenuTranslations(menuTranslationsList);
            }
            menuRepository.save(menu);

        } catch (Exception e) {
            throw new FoundException("Position exist");
        }
        return menuConverter.convertMenuToDto(menu, locale);

    }

    @Override
    public void deleteMenu(String locale, int id) {

        Optional<Menu> menu = menuRepository.findById(id);
        if (!menu.isPresent()) {
            throw new NotFoundException("Menu not found");
        }
        menuRepository.delete(menu.get());

    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            for (Integer id : ids
            ) {
                Optional<Menu> menu = menuRepository.findById(id);
                if (!menu.isPresent()) {
                    throw new NotFoundException("Menu not found");
                }
                menu.get().setDeletedAt(new Date());
                menuRepository.save(menu.get());
            }
            a = true;
        } catch (Exception e) {
            a = false;
        }
        return a;
    }
}
