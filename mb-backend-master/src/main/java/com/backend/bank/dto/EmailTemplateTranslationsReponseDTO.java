package com.backend.bank.dto;

import com.backend.bank.model.EmailTemplateTranslations;

public class EmailTemplateTranslationsReponseDTO {
    private int id ;
    private String locale;
    private String name;
    private String subject;
    private String content;
    private int emailTemplateId;


    public void EmailTemplateTranslations(EmailTemplateTranslations emailTemplateTranslations) {
        this.id = emailTemplateTranslations.getId();
        this.locale = emailTemplateTranslations.getLocale();
        this.name = emailTemplateTranslations.getName();
        this.subject = emailTemplateTranslations.getSubject();
        this.content = emailTemplateTranslations.getContext();
        this.emailTemplateId = emailTemplateTranslations.getEmailTemplate().getId();



    }
}

