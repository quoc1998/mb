package com.backend.bank.repository;

import com.backend.bank.model.GroupFAQTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupFAQTranslationRepository extends JpaRepository<GroupFAQTranslation, Integer> {
}
