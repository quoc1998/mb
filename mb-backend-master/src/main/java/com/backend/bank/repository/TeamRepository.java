package com.backend.bank.repository;

import com.backend.bank.model.Category;
import com.backend.bank.model.Team;
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
public interface TeamRepository extends JpaRepository<Team,Integer> {
    Optional<Team> findByName(String name);

   @Query(value = "select DISTINCT team_id from team", nativeQuery = true)
    List<Integer> findAllId();

    List<Team> findAllByLocalAndDeletedAt(String local, Date deleteAt);

    List<Team> findAllByTeamIdAndDeletedAt(Integer id, Date date);

    Optional<Team> findByTeamIdAndLocalAndDeletedAt(int id,String local, Date date);

    @Query(value = "from Team c where c.teamId in ?1")
    List<Team> findAllByTeamIds(List<Integer> ids);

    List<Team> findByLocal(String local);

    @Query(value = "from Team c where c.teamId =?1")
    List<Team> findAllByTeamId(int teamId);

    @Query(value = "select * from TEAM r where r.LOCAL =:locale and r.NAME like %:search%",
            nativeQuery = true)
    Page<Team> getBlocksSearch(Pageable pageable, @Param("locale") String locale, @Param("search") String search);

    @Query("SELECT b FROM Team b")
    Page<Team> getAllBy(Pageable pageable);

    @Query(value = "SELECT * FROM TEAM T WHERE T.TEAM_ID in ( " +
            "SELECT DISTINCT TEAM.TEAM_ID FROM  " +
            "                TEAM JOIN ROLE_TEAM ON TEAM.TEAM_ID = ROLE_TEAM.TEAM_ID" +
            "                JOIN ROLES ON ROLES.ROLE_ID = ROLE_TEAM.ROLE_ID" +
            "                JOIN USER_ROLE ON USER_ROLE.ROLE_ID = ROLES.ROLE_ID" +
            "                JOIN USERS ON USERS.USER_ID = USER_ROLE.USER_ID" +
            "                WHERE USERS.USER_NAME = ?1) AND T.LOCAL = ?2", nativeQuery = true)
    List<Team> findAllByUsername(String userName, String local);

    @Transactional
    @Modifying
    @Query("delete from Team i where i.teamId in ?1")
    void deleteAllById(List<Integer> ids);

    @Transactional
    @Modifying
    @Query("delete from Team i where i.teamId in ?1")
    void deleteByTeamId(Integer id);
}
