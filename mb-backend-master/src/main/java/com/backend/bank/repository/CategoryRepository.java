package com.backend.bank.repository;

import com.backend.bank.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select * from categories c WHERE c.deleted_at is null", nativeQuery = true)
    List<Category> findAll();


    @Query(value = "select * from categories c WHERE c.IS_ACTIVE = 1 and c.deleted_at is null", nativeQuery = true)
    List<Category> findAllFrontend();

    @Query(value = "select * from categories c WHERE c.ID = ?1 and c.IS_ACTIVE = 1 and c.deleted_at is null", nativeQuery = true)
    Category findFrontendById(Integer id);

    List<Category> findAllByCategory(Category category);

    @Query(value = "select * from categories  join category_translations on " +
            "category_translations.categorys_id = categories.id and " +
            "category_translations.name = ?1 and categories.deleted_at is null", nativeQuery = true)
    Category findByCategoryByName(String name);

    @Query(value = "SELECT * FROM CATEGORIES c WHERE c.id in ( SELECT DISTINCT CATEGORIES.ID FROM CATEGORIES" +
            "                JOIN CATEGORY_TEAM ON CATEGORY_TEAM.CATEGORY_ID = CATEGORIES.ID" +
            "                JOIN TEAM ON TEAM.TEAM_ID = CATEGORY_TEAM.TEAM_ID" +
            "                JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                WHERE USERS.USER_NAME = ?1 and c.deleted_at is null)", nativeQuery = true)
    List<Category> findAllByUsername(String userName);

    @Query(value = "select * from categories  join category_translations on " +
            "category_translations.categorys_id = categories.id and " +
            "category_translations.slug = ?1 and categories.deleted_at is null", nativeQuery = true)
    List<Category> findAllBySlug(String slug);
}
