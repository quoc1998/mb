package com.backend.bank.repository;

import com.backend.bank.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByName(String name);
    List<Role> findByLocal(String local);
    Optional<Role> findByRoleIdAndLocal(int id,String local);
    List<Role> findByRoleId(int id);



    @Query("SELECT b FROM Role b")
    Page<Role> getAllBy(Pageable pageable);

    @Query(
            value = "select user_id from user_role where role_id = ?1",
            nativeQuery = true)
    List<Integer> deleteUserOfRole(int id);

    @Query(value = "select * from ROLES r where r.LOCAL =:locale and r.NAME like %:search%",
            nativeQuery = true)
    Page<Role> getBlocksSearch(Pageable pageable, @Param("locale") String locale, @Param("search") String search);
}
