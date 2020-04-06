package com.backend.bank.repository;

import com.backend.bank.model.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Integer> {
    @Query("from FAQ f where f.id = ?1")
    Optional<FAQ> findById(Integer id);

    @Query(value = "SELECT * FROM FAQS JOIN FAQ_TRANSLATIONS ON FAQ_TRANSLATIONS.FAQ_ID = FAQS.ID and FAQ_TRANSLATIONS.QUESTION like %:name%", nativeQuery = true)
    List<FAQ> searchFAQ(@Param("name") String name);
}
