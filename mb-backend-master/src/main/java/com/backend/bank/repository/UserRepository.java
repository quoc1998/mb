package com.backend.bank.repository;

import com.backend.bank.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

//    UserResponseDto findByUsername(@Param("userName") String username);
    Optional<User> findByUserName(String username);

    Optional<User> findById(Integer id);

    @Query("SELECT b FROM User b")
    Page<User> getAllBy(Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from User i where i.id in ?1")
    void deleteAllById(List<Integer> ids);

    @Query(value = "SELECT * FROM USERS f where f.USER_NAME LIKE %:userName% ",
            nativeQuery = true)
    Page<User> getUserByNameSearch(Pageable pageable, @Param("userName") String userName);
}
