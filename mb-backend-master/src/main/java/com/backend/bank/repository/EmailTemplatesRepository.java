package com.backend.bank.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.EmailTemplate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTemplatesRepository extends JpaRepository<EmailTemplate, Integer>, JpaSpecificationExecutor<EmailTemplate> {
    Optional<EmailTemplate> findById(Integer id);

    List<EmailTemplate> findAllByEmailTemplateTranslations_Locale(String local);



    //List<EmailTemplate> findAllBy(String local);

    @Query(value = "select * from MAIL_TEMPLATES join MAIL_TEMPLATES_TRANSLATIONS on " +
            "MAIL_TEMPLATES_TRANSLATIONS.EMAIL_TEMPLATE_ID = MAIL_TEMPLATES.ID and " +
            "MAIL_TEMPLATES_TRANSLATIONS.LOCALE = ?1 and MAIL_TEMPLATES_TRANSLATIONS.name like %?2%", nativeQuery = true)
    Page<EmailTemplate> searchMailTemplate(Pageable pageable, String local, String search);




    @Transactional
    @Modifying
    @Query("delete from EmailTemplate i where i.id in ?1")
    void deleteAllById(List<Integer> ids);
}