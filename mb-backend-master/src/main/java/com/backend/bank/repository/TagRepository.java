package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer>, JpaSpecificationExecutor<Tag> {
    @Query("from Tag t where t.id = ?1")
    Optional<Tag> findById(Integer id);

    List<Tag> findAllByLocal(String local);
}