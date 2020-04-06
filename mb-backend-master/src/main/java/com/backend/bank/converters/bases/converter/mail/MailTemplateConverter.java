package com.backend.bank.converters.bases.converter.mail;

import com.backend.bank.dto.request.EmailTemplateRequestDto;
import com.backend.bank.dto.response.SendMailResponse;
import com.backend.bank.dto.response.setting.EmailTemplateRoponseDto;
import com.backend.bank.model.EmailTemplate;
import com.backend.bank.model.EmailTemplateTranslations;
import com.backend.bank.model.SendMailEntity;
import com.backend.bank.repository.EmailTemplateTranslationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MailTemplateConverter {
    @Autowired
    private EmailTemplateTranslationsRepository emailTemplateTranslationsRepository;

    public EmailTemplate converterEntity(String local, EmailTemplateRequestDto emailTemplateRequestDto) {
        EmailTemplate emailTemplate = new EmailTemplate();
        Date date = new Date();
        emailTemplate.setActive(emailTemplateRequestDto.getActive());
        emailTemplate.setCode(emailTemplateRequestDto.getCode());
        emailTemplate.setCreatedAt(date);
        emailTemplate.setEmailCc(emailTemplateRequestDto.getEmailCc());
        List<EmailTemplateTranslations> emailTemplateTranslationsList = new ArrayList<>();
        EmailTemplateTranslations emailTemplateTranslations = new EmailTemplateTranslations();
        emailTemplateTranslations.setContext(emailTemplateRequestDto.getContent());
        emailTemplateTranslations.setLocale(local);
        emailTemplateTranslations.setSubject(emailTemplateRequestDto.getSubject());
        emailTemplateTranslations.setEmailTemplate(emailTemplate);
        emailTemplateTranslations.setName(emailTemplateRequestDto.getName());
        emailTemplateTranslationsList.add(emailTemplateTranslations);
        emailTemplate.setEmailTemplateTranslations(emailTemplateTranslationsList);
        return emailTemplate;
    }

    public List<EmailTemplateRoponseDto> converterListEmailDto(List<EmailTemplate> emailTemplates, String local) {
        List<EmailTemplateRoponseDto> emailTemplateRoponseDtos = new ArrayList<>();
        for (EmailTemplate emailTemplate : emailTemplates) {
            EmailTemplateRoponseDto emailTemplateRoponseDto = this.converterEmailTemplate(emailTemplate, local);
            emailTemplateRoponseDtos.add(emailTemplateRoponseDto);
        }
        return emailTemplateRoponseDtos;
    }

    public EmailTemplateRoponseDto converterEmailTemplate(EmailTemplate emailTemplate, String local) {
        EmailTemplateRoponseDto emailTemplateRoponseDto = new EmailTemplateRoponseDto();
        emailTemplateRoponseDto.setActive(emailTemplate.getActive());
        emailTemplateRoponseDto.setId(emailTemplate.getId());
        emailTemplateRoponseDto.setCode(emailTemplate.getCode());
        EmailTemplateTranslations emailTemplateTranslations = emailTemplateTranslationsRepository.findByEmailTemplateAndLocale(emailTemplate, local).orElse(null);
        if (emailTemplateTranslations != null) {
            emailTemplateRoponseDto.setName(emailTemplateTranslations.getName());
            emailTemplateRoponseDto.setSubject(emailTemplateTranslations.getSubject());
            emailTemplateRoponseDto.setContent(emailTemplateTranslations.getContext());
            emailTemplateRoponseDto.setEmailCc(emailTemplate.getEmailCc());
        } else {
            EmailTemplateTranslations emailTemplateTranslations1 = new EmailTemplateTranslations();
            emailTemplateTranslations1.setLocale(local);
            emailTemplateTranslations1.setContext("");
            emailTemplateTranslations1.setName(emailTemplate.getEmailTemplateTranslations().get(0).getName());
            emailTemplateTranslations1.setSubject("");
            emailTemplateTranslations1.setEmailTemplate(emailTemplate);
            emailTemplateTranslationsRepository.save(emailTemplateTranslations1);
            emailTemplateRoponseDto.setName(emailTemplateTranslations1.getName());
            emailTemplateRoponseDto.setSubject("");
            emailTemplateRoponseDto.setContent("");
            emailTemplateRoponseDto.setEmailCc("");
        }
        return emailTemplateRoponseDto;
    }
}
