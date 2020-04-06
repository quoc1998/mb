package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.common.MenuItemSortByPosition;
import com.backend.bank.converters.bases.converter.menu.MenuItemConverter;
import com.backend.bank.dto.request.MenuItemsRequestDto;
import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.MenuItemTranslations;
import com.backend.bank.model.MenuItems;
import com.backend.bank.repository.MenuItemTranslationRepository;
import com.backend.bank.repository.MenuItemsRepository;
import com.backend.bank.repository.PagesRepository;
import com.backend.bank.service.MenuItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuItemsServiceImpl implements MenuItemsService {

    @Autowired
    MenuItemsRepository menuItemsRepository;

    @Autowired
    MenuItemConverter menuItemConverter;

    @Autowired
    MenuItemTranslationRepository menuItemTranslationRepository;

    @Autowired
    PagesRepository pagesRepository;

    @Override
    public List<MenuItemsReponseDto> findAllMenuItem(String local, Integer id) {
        List<MenuItems> menuItems = menuItemsRepository.findAllByMenu_Id(id);
        List<MenuItemsReponseDto> menuItemsReponseDtos = menuItemConverter.converterListMenuItem(menuItems, local);
        return menuItemsReponseDtos;
    }

    @Override
    public MenuItemsReponseDto addMenuItems(MenuItemsRequestDto menuItemsRequestDto, String local, Integer idMenu) {
        List<MenuItems> menuItemsList = menuItemsRepository.findByMenuItems(null);
        if (menuItemsRequestDto.getParentId() == null && menuItemsList.size() == 0) {
            menuItemsRequestDto.setPosition(1);

        } else if (menuItemsRequestDto.getParentId() == null && menuItemsList.size() != 0) {
            menuItemsRequestDto.setPosition(menuItemsList.size() + 1);
        } else {
            List<MenuItemsReponseDto> menuItemsReponseDtos = sortMenuItem(menuItemsRequestDto.getParentId(), local);
            if (menuItemsReponseDtos.size() == 0) {
                menuItemsRequestDto.setPosition(1);
            } else {
                menuItemsRequestDto.setPosition(menuItemsReponseDtos.get(menuItemsReponseDtos.size() - 1).getPosition() + 1);
            }
        }

        MenuItems menuItems = menuItemConverter.converterMenuItemDto(local, menuItemsRequestDto, idMenu);
        menuItemsRepository.save(menuItems);
        return menuItemConverter.converterEntity(menuItems, local);
    }

    @Override
    public List<MenuItemsReponseDto> findById(Integer menuId, String local, Integer menuItemId) {
        Optional<MenuItems> menuItems = menuItemsRepository.findById(menuItemId);
        if (!menuItems.isPresent()) {
            throw new NotFoundException("Menuitem Not found");
        }
        List<MenuItems> menuItemsList = menuItemsRepository.findAllByMenu_IdAndMenuItems(menuId, menuItems.get());
        List<MenuItemsReponseDto> menuItemsReponseDtos = menuItemConverter.converterListMenuItem(menuItemsList, local);
        return menuItemsReponseDtos;
    }

    @Override
    public MenuItemsReponseDto editMenuItems(Integer menuId, Integer menuItemId,
                                             MenuItemsRequestDto menuItemsRequestDto, String local) {
        MenuItems menuItems = menuItemsRepository.findById(menuItemId).get();


        menuItems.setActive(menuItemsRequestDto.getActive());
        menuItems.setFluid(menuItemsRequestDto.getFluid());
        menuItems.setRoot(menuItemsRequestDto.getRoot());
        menuItems.setPosition(menuItemsRequestDto.getPosition());
        menuItems.setCategoryId(menuItemsRequestDto.getCategoryId());
        if (menuItemsRequestDto.getPagesId() != null) {
            menuItems.setPages(pagesRepository.findById(menuItemsRequestDto.getPagesId()).get());
        }
        menuItems.setSlugPages(menuItemsRequestDto.getSlugPages());
        menuItems.setType(menuItemsRequestDto.getType());
        menuItems.setTargetId(menuItemsRequestDto.getTargetId());
        menuItems.setRoot(menuItemsRequestDto.getRoot());
        if (menuItemsRequestDto.getSlug() == null || menuItemsRequestDto.getSlug().trim().equals("")) {
            menuItems.setSlug(Constants.toSlug(menuItemsRequestDto.getName()));
        } else {
            menuItems.setSlug(Constants.toSlug(menuItemsRequestDto.getSlug()));
        }
        menuItems.setIcon(menuItemsRequestDto.getIcon());
        menuItems.setUrl(menuItemsRequestDto.getUrl());
        menuItems.setCategoryNewId(menuItemsRequestDto.getCategoryNewId());

        Date date = new Date();
        if (menuItemsRequestDto.getParentId() != null) {
            MenuItems menuItems1 = menuItemsRepository.findById(menuItemsRequestDto.getParentId()).get();
            menuItems.setMenuItems(menuItems1);
        }
        MenuItemTranslations menuItemTranslations = menuItemTranslationRepository.findByMenuItemsAndLocale(menuItems, local).get();
        menuItemTranslations.setName(menuItemsRequestDto.getName());
        menuItemTranslations.setDescription(menuItemsRequestDto.getDescription());
        menuItemTranslations.setMenuItems(menuItems);
        menuItems.setUpdateAt(date);
        menuItemsRepository.save(menuItems);
        return menuItemConverter.converterEntity(menuItems, local);
    }


    @Override
    public int deleteMenuItems(Integer id, Integer menuid) {
        menuItemsRepository.deleteById(id);
        return menuid;
    }

    @Override
    public List<MenuItemsReponseDto> findAllByLocal(String local) {
        return null;
    }

    @Override
    public Integer updatePosition(Integer parentId, int id, int position, String local)
    // chỉnh lại.
    {
        Constants.checkLocal(local);
        if (parentId != -1) {
            MenuItems menuItems = menuItemsRepository.findById(parentId).get();
            if (menuItems == null) {
                throw new NotFoundException("MenuItem parent not found");
            }
            Optional<MenuItems> menuItem = menuItemsRepository.findById(id);
            if (menuItem.get().getMenuItems() != null) {
                if (menuItem.get().getPosition().equals(position) && menuItem.get().getMenuItems().getId().equals(parentId)) {
                    return menuItem.get().getMenu().getId();
                }
            }
            if (!menuItem.isPresent()) {
                throw new NotFoundException("MenuItem not found");
            }

            if (id != parentId) {
                menuItem.get().setMenuItems(menuItems);
                menuItem.get().setPosition(menuItems.getMenuItemsList().size() + 1);
                menuItems.getMenuItemsList().add(menuItem.get());
                menuItemsRepository.save(menuItem.get());
            }
            List<MenuItems> menuItemsList = menuItems.getMenuItemsList();
            Collections.sort(menuItemsList, new MenuItemSortByPosition());

            int first_position = -1, last_position = -1;
            for (int i = 0; i < menuItemsList.size(); i++) {
                if (menuItemsList.get(i).getId() == id) {
                    first_position = i;
                }
                if (menuItemsList.get(i).getPosition() == position) {
                    last_position = i;
                }
            }

            if (menuItemsList.size() != 1) {
                if (first_position < last_position) {
                    for (int i = last_position; i > first_position; i--) {
                        menuItemsList.get(i).setPosition(menuItemsList.get(i - 1).getPosition());
                        menuItemsRepository.save(menuItemsList.get(i));
                    }
                } else {
                    for (int i = last_position; i < first_position; i++) {
                        menuItemsList.get(i).setPosition(menuItemsList.get(i + 1).getPosition());
                        menuItemsRepository.save(menuItemsList.get(i));
                    }
                }
            }
            menuItem.get().setPosition(position);
            menuItemsRepository.save(menuItem.get());
            return menuItem.get().getMenu().getId();
        } else {
            MenuItems menuItem = menuItemsRepository.findById(id).orElse(null);
            List<MenuItems> menuItems = menuItemsRepository.findByMenuAndMenuItems(menuItem.getMenu(), null);
            if (menuItem == null) {
                throw new NotFoundException("MenuItem Not Found");
            }
            Collections.sort(menuItems, new MenuItemSortByPosition());
            if (menuItem.getPosition().equals(position)) {
                return menuItem.getMenu().getId();
            }
            int first_position = -1, last_position = -1;
            for (int i = 0; i < menuItems.size(); i++) {
                if (menuItems.get(i).getId() == id) {
                    first_position = i;
                }
                if (menuItems.get(i).getPosition() == position) {
                    last_position = i;
                }
            }

            if (menuItems.size() != 1) {
                if (first_position < last_position) {

                    if (last_position != -1) {
                        for (int i = last_position; i > first_position; i--) {
                            menuItems.get(i).setPosition(menuItems.get(i - 1).getPosition());
                            menuItemsRepository.save(menuItems.get(i));
                        }
                    }
                } else {
                    if (last_position != -1) {
                        for (int i = last_position; i < first_position; i++) {
                            menuItems.get(i).setPosition(menuItems.get(i + 1).getPosition());
                            menuItemsRepository.save(menuItems.get(i));
                        }
                    }
                }
            }
            menuItem.setPosition(position);
            menuItem.setMenuItems(null);
            menuItemsRepository.save(menuItem);
            return menuItem.getMenu().getId();
        }


    }

    @Override
    public List<MenuItemsReponseDto> sortMenuItem(Integer parentId, String local) {
        Constants.checkLocal(local);
        if (parentId == -1) {
            parentId = null;
        }
        List<MenuItemsReponseDto> menuItemsReponseDtos;

        Optional<MenuItems> menuItems = menuItemsRepository.findById(parentId);
        if (parentId != 0 && !menuItems.isPresent()) {
            throw new NotFoundException("MenuItem parent not found!");
        }
        List<MenuItems> menuItemsList = menuItemsRepository.findByMenuItems(menuItems.get());
        if (menuItemsList.isEmpty()) {
            menuItemsReponseDtos = new ArrayList<>();
        } else {
            Collections.sort(menuItemsList, new MenuItemSortByPosition());
            menuItemsReponseDtos = menuItemConverter.converterListMenuItem(menuItemsList, local);
        }

        return menuItemsReponseDtos;
    }
}


