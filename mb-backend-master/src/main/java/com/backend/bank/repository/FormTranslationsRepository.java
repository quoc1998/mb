package com.backend.bank.repository;

import com.backend.bank.model.FormTranslations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface FormTranslationsRepository extends JpaRepository<FormTranslations, Integer> {


    @Query(value = "select * from form_translations f where f.forms_id = ?1 and f.local = ?2",
            nativeQuery = true)
    FormTranslations findByForm_IdAndLocal(int formId, String local);

}
