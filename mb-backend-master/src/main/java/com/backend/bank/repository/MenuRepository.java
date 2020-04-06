package com.backend.bank.repository;

import com.backend.bank.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    Optional<Menu> findByPosition(String position);

    List<Menu> findAllByDeletedAt(Date deletedAt);
    List<Menu> findAllByPosition(String position);


    Menu findByDeletedAtAndId(Date date, Integer id);

    List<Menu> findAllByPositionAndDeletedAt(String position, Date deletedAt);

    @Transactional
    @Modifying
    @Query("delete from Menu i where i.id in ?1")
    void deleteAllById(List<Integer> ids);

    @Query(value = "select * from menu m join MENU_TRANSLATIONS on m.ID = MENU_TRANSLATIONS.MENU_ID and \n" +
            "MENU_TRANSLATIONS.LOCALE =:locale and MENU_TRANSLATIONS.NAME like %:name%;",
            nativeQuery = true)
    Page<Menu> getMenuSearch(Pageable pageable, @Param("locale") String locale, @Param("name") String name);
}
