package com.backend.bank.repository;

import com.backend.bank.model.MenuItemTranslations;
import com.backend.bank.model.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemTranslationRepository extends JpaRepository<MenuItemTranslations, Integer> {
    Optional<MenuItemTranslations> findByMenuItemsAndLocale(MenuItems menuItems,String local);
}
