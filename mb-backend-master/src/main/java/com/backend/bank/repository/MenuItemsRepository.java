package com.backend.bank.repository;

import com.backend.bank.model.Menu;
import com.backend.bank.model.Pages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.MenuItems;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuItemsRepository extends JpaRepository<MenuItems, Integer>, JpaSpecificationExecutor<MenuItems> {

    List<MenuItems> findAllByMenu_IdAndMenuItems(Integer id, MenuItems menuItems);

    @Query("from MenuItems m where m.id = ?1")
    Optional<MenuItems> findById(Integer id);

    List<MenuItems> findAllByMenuItems(MenuItems menuItems);
    List<MenuItems> findAllByMenu_Id(Integer id);
    List<MenuItems> findByMenuItems(MenuItems menuItems);
    List<MenuItems> findByMenuAndMenuItems(Menu menu, MenuItems menuItems);
    List<MenuItems> findByPages(Pages pages);
    List<MenuItems> findAllBySlugPages(String slug);




}