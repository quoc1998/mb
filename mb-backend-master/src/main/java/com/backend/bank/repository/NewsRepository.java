package com.backend.bank.repository;

import com.backend.bank.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

    @Query(value = "select * from news JOIN news_translations on news.id = news_translations.news_id WHERE news_translations.is_active = 1",
            nativeQuery = true)
    List<News> findAllFrontend();
    List<News> findAll();

    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news JOIN NEW_CATEGORY ON news.id = NEW_CATEGORY.new_id" +
            "                JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN CATEGORY_TEAM ON CATEGORY_TEAM.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN TEAM ON TEAM.TEAM_ID = CATEGORY_TEAM.TEAM_ID" +
            "                JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                WHERE USERS.USER_NAME = ?1)",
            nativeQuery = true)
    Page<News> findAllByUser(Pageable pageable, String username);

    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news JOIN NEW_CATEGORY ON news.id = NEW_CATEGORY.new_id" +
            "                JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN CATEGORY_TEAM ON CATEGORY_TEAM.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN TEAM ON TEAM.TEAM_ID = CATEGORY_TEAM.TEAM_ID" +
            "                JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                JOIN NEWS_TRANSLATIONS ON NEWS_TRANSLATIONS.NEWS_ID = NEWS.ID" +
            "                WHERE USERS.USER_NAME = ?1 and NEWS_TRANSLATIONS.IS_ACTIVE = ?2 and NEWS_TRANSLATIONS.LOCALE = ?3)",
            nativeQuery = true)
    Page<News> findAllByIsActive(Pageable pageable, String username, Boolean isActive, String local);

    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news JOIN NEW_CATEGORY ON news.id = NEW_CATEGORY.new_id" +
            "                JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN CATEGORY_TEAM ON CATEGORY_TEAM.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN TEAM ON TEAM.TEAM_ID = CATEGORY_TEAM.TEAM_ID" +
            "                JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                JOIN NEWS_TRANSLATIONS ON NEWS_TRANSLATIONS.NEWS_ID = NEWS.ID" +
            "                WHERE USERS.USER_NAME = ?1 and NEWS_TRANSLATIONS.IS_ACTIVE = ?2 and NEWS_TRANSLATIONS.LOCALE = ?3)",
            nativeQuery = true)
    List<News> findAllByIsActiveNotPagination(String username, Boolean isActive, String local);

    @Query("from News n where n.id in ?1")
    List<News> findAllById(List<Integer> ids);


    @Query("SELECT b FROM News b")
    Page<News> getAllBy(Pageable pageable);


    @Transactional
    @Modifying
    @Query("delete from News i where i.id in ?1")
    void deleteAllById(List<Integer> ids);

    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news JOIN NEW_CATEGORY ON " +
            "                               news.id = NEW_CATEGORY.new_id" +
            "                            JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                            JOIN CATEGORY_TEAM ON CATEGORY_TEAM.CATEGORY_ID = CATEGORIES.ID" +
            "                            JOIN TEAM ON TEAM.TEAM_ID = CATEGORY_TEAM.TEAM_ID" +
            "                            JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                         JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                            JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                            JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                           JOIN NEWS_TRANSLATIONS ON NEWS_TRANSLATIONS.NEWS_ID = NEWS.ID" +
            "                           WHERE USERS.USER_NAME =:username" +
            "                         AND NEWS_TRANSLATIONS.LOCALE =:locale and NEWS_TRANSLATIONS.title like %:search% " +
            "                         and CATEGORIES.ID like %:categoryId%)",
            nativeQuery = true)
    Page<News> findAllByTitle(Pageable pageable, @Param("username") String username, @Param("locale") String locale,
                              @Param("search") String search, @Param("categoryId") String categoryId);

    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news " +
            "                            JOIN NEW_CATEGORY ON news.id = NEW_CATEGORY.new_id" +
            "                         JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                            JOIN CATEGORY_TEAM ON CATEGORY_TEAM.CATEGORY_ID = CATEGORIES.ID" +
            "                            JOIN TEAM ON TEAM.TEAM_ID = CATEGORY_TEAM.TEAM_ID" +
            "                            JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                            JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                            JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                            JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                            JOIN NEWS_TRANSLATIONS ON NEWS_TRANSLATIONS.NEWS_ID = NEWS.ID" +
            "                            WHERE USERS.USER_NAME = ?1 AND NEWS_TRANSLATIONS.LOCALE = ?2 " +
            "                           and CATEGORIES.ID = ?3)",
            nativeQuery = true)
    Page<News> findAllByCategoryId(Pageable pageable, String username, String local, Integer categoryId);

    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news " +
            "                                        JOIN NEW_CATEGORY ON news.id = NEW_CATEGORY.new_id" +
            "                                        JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                                        JOIN CATEGORY_TRANSLATIONS ON CATEGORY_TRANSLATIONS.CATEGORYS_ID = CATEGORIES.ID" +
            "                                    JOIN NEWS_TRANSLATIONS ON NEWS_TRANSLATIONS.NEWS_ID = NEWS.ID" +
            "                                       WHERE  NEWS_TRANSLATIONS.LOCALE = ?1 " +
            "                                      and CATEGORY_TRANSLATIONS.SLUG = ?2)",
            nativeQuery = true)
    Page<News> findAllByCategorySlug(Pageable pageable, String local, String categorySlug);


    @Query(value = "select * FROM news n WHERE ID IN (SELECT DISTINCT news.ID FROM news " +
            "                            JOIN NEW_CATEGORY ON news.id = NEW_CATEGORY.new_id" +
            "                            JOIN CATEGORIES ON NEW_CATEGORY.CATEGORY_ID = CATEGORIES.ID" +
            "                            JOIN NEWS_TRANSLATIONS ON NEWS_TRANSLATIONS.NEWS_ID = NEWS.ID" +
            "                            WHERE  NEWS_TRANSLATIONS.LOCALE = ?1 " +
            "                           and CATEGORIES.ID = ?2 and to_char(create_at,'YYYY')= ?3)",
            nativeQuery = true)
    Page<News> findAllByCategoryIdAndYear(Pageable pageable, String local, Integer categoryId, Integer year);
}
