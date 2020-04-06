package com.backend.bank.repository;


import com.backend.bank.model.Menu;

import com.backend.bank.model.MenuTranslations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface MenuTranslationRepository extends JpaRepository<MenuTranslations, Integer> {
    Optional<MenuTranslations> findByMenuAndLocale(Menu menu, String local);

}
