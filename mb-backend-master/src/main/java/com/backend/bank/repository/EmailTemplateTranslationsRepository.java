package com.backend.bank.repository;

import com.backend.bank.model.EmailTemplate;
import com.backend.bank.model.EmailTemplateTranslations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTemplateTranslationsRepository extends JpaRepository<EmailTemplateTranslations, Integer> {

    List<EmailTemplateTranslations> findAll();

    Optional<EmailTemplateTranslations> findByEmailTemplateAndLocale(EmailTemplate emailTemplate, String local);

    List<EmailTemplateTranslations> findByEmailTemplate(EmailTemplate emailTemplate);

}