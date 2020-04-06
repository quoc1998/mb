package com.backend.bank.repository;

import com.backend.bank.model.News;
import com.backend.bank.model.PageTranslations;
import com.backend.bank.model.Pages;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface PagesRepository extends JpaRepository<Pages,Integer> {
    List<Pages> findAllByDeletedAt(Date date);

    List<Pages> findAllByParentId(Integer parentId);

    @Query("from Pages n where n.id in ?1")
    List<Pages> findAllById(List<Integer> ids);

    Pages findByIdAndDeletedAt(Integer id, Date date);

    List<Pages> findByParentId(int parentId);
    Optional<Pages> findByPosition(int position);

    @Query("SELECT b FROM Pages b")
    Page<Pages> getAllBy(Pageable pageable);

    @Query(value = "select * from PAGES m join PAGE_TRANSLATIONS on m.ID = PAGE_TRANSLATIONS.PAGES_ID and" +
            "PAGE_TRANSLATIONS.LOCALE =:locale and PAGE_TRANSLATIONS.NAME like %:name%;",
            nativeQuery = true)
    Page<Pages> getPagesSearch(Pageable pageable, @Param("locale") String locale, @Param("name") String name);

    @Query(value = "select * FROM PAGES p WHERE p.ID IN (SELECT DISTINCT PAGES.ID FROM PAGES " +
            "                          JOIN TEAM ON TEAM.TEAM_ID = PAGES.TEAM_ID" +
            "                          JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                          JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                          JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                          JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                          WHERE USERS.USER_NAME = ?1)",
            nativeQuery = true)
    Page<Pages> findAllByUser(Pageable pageable, String username);

    @Query(value = "select * FROM PAGES p WHERE p.ID IN (SELECT DISTINCT PAGES.ID FROM PAGES " +
            "                          JOIN TEAM ON TEAM.TEAM_ID = PAGES.TEAM_ID" +
            "                          JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                          JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                          JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                          JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                          WHERE USERS.USER_NAME = ?1 AND PAGES.IS_ACTIVE = ?2)",
            nativeQuery = true)
    Page<Pages> findAllByIsActive(Pageable pageable, String username, Boolean isActive);

    @Query(value = "select * FROM PAGES p WHERE p.ID IN (SELECT DISTINCT PAGES.ID FROM PAGES " +
            "                          JOIN TEAM ON TEAM.TEAM_ID = PAGES.TEAM_ID" +
            "                          JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                          JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                          JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                          JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                          JOIN PAGE_TRANSLATIONS ON PAGES.ID = PAGE_TRANSLATIONS.PAGES_ID" +
            "                          WHERE USERS.USER_NAME = ?1 AND PAGES.IS_ACTIVE = ?2 " +
            "                           and PAGE_TRANSLATIONS.NAME LIKE %?3%)",
            nativeQuery = true)
    Page<Pages> searchPages(Pageable pageable, String username, Boolean isActive, String search);
}
