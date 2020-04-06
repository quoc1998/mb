package com.backend.bank.repository;

import com.backend.bank.model.Forms;
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
public interface FormRepository extends JpaRepository<Forms, Integer> {
    List<Forms> findAllByFormTranslations_Local(String local);

    Optional<Forms> findByIdAndFormTranslations_Local(Integer id, String local);

    @Query("from Forms i where i.id in ?1")
    Optional<Forms> findById(Integer id);

    @Query("SELECT b FROM Forms b")
    Page<Forms> getAllBy(Pageable pageable);

    @Transactional
    @Modifying
    @Query("delete from Forms i where i.id in ?1")
    void deleteAllById(List<Integer> ids);

    @Query(value = "select * from FORMS m join FORM_TRANSLATIONS on m.ID = FORM_TRANSLATIONS.FORMS_ID and \n" +
            "FORM_TRANSLATIONS.LOCAL =:locale and FORM_TRANSLATIONS.NAME like %:name%",
            nativeQuery = true)
    Page<Forms> getFormSearch(Pageable pageable, @Param("locale") String locale, @Param("name") String name);
}
