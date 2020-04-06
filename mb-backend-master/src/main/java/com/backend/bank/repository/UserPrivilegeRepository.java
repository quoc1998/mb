package com.backend.bank.repository;

import com.backend.bank.model.Privilege;
import com.backend.bank.model.User;
import com.backend.bank.model.UserPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserPrivilegeRepository extends JpaRepository<UserPrivilege, Integer> , JpaSpecificationExecutor<UserPrivilege>{
    Optional<UserPrivilege> findByUserAndPrivilege(User user, Privilege privilege);

    @Transactional
    @Modifying
    @Query(value = "delete FROM user_privilege where user_privilege.user_id =?1 ", nativeQuery = true)
    void deleteAllByUser(Integer userId);
}