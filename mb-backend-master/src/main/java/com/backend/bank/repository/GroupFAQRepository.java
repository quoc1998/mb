package com.backend.bank.repository;

import com.backend.bank.model.GroupFAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface GroupFAQRepository extends JpaRepository<GroupFAQ, Integer> {
    @Query("from GroupFAQ f where f.id = ?1")
    Optional<GroupFAQ> findById(Integer id);
}
