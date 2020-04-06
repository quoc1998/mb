package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.FeedbackTranslation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedbackTranslationRepository extends JpaRepository<FeedbackTranslation, Integer>, JpaSpecificationExecutor<FeedbackTranslation> {
    @Query("from FeedbackTranslation f where f.id=?1")
    Optional<FeedbackTranslation> findById(Integer id);
}