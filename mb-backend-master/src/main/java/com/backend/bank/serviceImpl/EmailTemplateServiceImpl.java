package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.mail.MailTemplateConverter;
import com.backend.bank.dto.request.EmailTemplateRequestDto;
import com.backend.bank.dto.response.block.BlocksReponseDTO;
import com.backend.bank.dto.response.block.PaginationBlock;
import com.backend.bank.dto.response.setting.EmailTemplateRoponseDto;
import com.backend.bank.dto.response.setting.PaginationEmailTemplate;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Blocks;
import com.backend.bank.model.EmailTemplate;
import com.backend.bank.model.EmailTemplateTranslations;
import com.backend.bank.repository.EmailTemplateTranslationsRepository;
import com.backend.bank.repository.EmailTemplatesRepository;
import com.backend.bank.service.EmailTemplatesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class EmailTemplateServiceImpl implements EmailTemplatesService {
    @Autowired
    EmailTemplatesRepository emailTemplatesRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    MailTemplateConverter mailTemplateConverter;
    @Autowired
    EmailTemplateTranslationsRepository emailTemplateTranslationsRepository;

    @Override
    public List<EmailTemplateRoponseDto> findAll(String local) {

        List<EmailTemplate> emailTemplateList = emailTemplatesRepository.findAll();
        List<EmailTemplateRoponseDto> emailTemplateRoponseDtos = mailTemplateConverter.converterListEmailDto(emailTemplateList, local);
        return emailTemplateRoponseDtos;
    }

    @Override
    public PaginationEmailTemplate searchMail(String locale, String search, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        //Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number);
        Page<EmailTemplate> pageAll = emailTemplatesRepository.searchMailTemplate(pageable,locale,  search);
        List<EmailTemplateRoponseDto> pagesResponseDtos = mailTemplateConverter.converterListEmailDto( pageAll.getContent(), locale);
        PaginationEmailTemplate paginationEmailTemplate = new PaginationEmailTemplate();
        paginationEmailTemplate.setEmailTemplate(pagesResponseDtos);
        paginationEmailTemplate.setSize(pageAll.getTotalPages());
        return paginationEmailTemplate;
    }

    @Override
    public EmailTemplateRoponseDto findById(String local, Integer id) {
        EmailTemplateRoponseDto emailTemplateRoponseDto;
        try {
            emailTemplateRoponseDto = mailTemplateConverter.converterEmailTemplate(emailTemplatesRepository.findById(id).get(), local);
        } catch (Exception e) {
            throw new NotFoundException("Email not Found");
        }
        return emailTemplateRoponseDto;
    }

    @Override
    public Boolean deleteById(String local, Integer id) {
        Boolean aBoolean = false;
        try {
            emailTemplatesRepository.deleteById(id);
            aBoolean = true;
        } catch (Exception e) {
            aBoolean = false;
        }
        return aBoolean;
    }

    @Override
    public EmailTemplateRoponseDto addEmailTemplate(String local, EmailTemplateRequestDto emailTemplateRequestDto) {

        try {
            EmailTemplate emailTemplate = mailTemplateConverter.converterEntity(local, emailTemplateRequestDto);
            emailTemplatesRepository.save(emailTemplate);
            EmailTemplateRoponseDto emailTemplateRoponseDto = mailTemplateConverter.converterEmailTemplate(emailTemplate, local);
            return emailTemplateRoponseDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not add Email");
        }

    }

    @Override
    public EmailTemplateRoponseDto editEmailTemplate(String local, Integer idMail, EmailTemplateRequestDto emailTemplateRequestDto) {

        try {
            EmailTemplate emailTemplate = emailTemplatesRepository.findById(idMail).get();
            if (emailTemplate != null) {
                Date date = new Date();
                emailTemplate.setActive(emailTemplateRequestDto.getActive());
                emailTemplate.setCode(emailTemplateRequestDto.getCode());
                emailTemplate.setUpdatedAt(date);
                emailTemplate.setEmailCc(emailTemplateRequestDto.getEmailCc());

                EmailTemplateTranslations emailTemplateTranslations = emailTemplateTranslationsRepository.findByEmailTemplateAndLocale(emailTemplate, local).orElse(null);

                if (emailTemplateTranslations != null) {
                    emailTemplateTranslations.setName(emailTemplateRequestDto.getName());
                    emailTemplateTranslations.setContext(emailTemplateRequestDto.getContent());
                    emailTemplateTranslations.setSubject(emailTemplateRequestDto.getSubject());
                    emailTemplateTranslationsRepository.save(emailTemplateTranslations);
                } else {
                    EmailTemplateTranslations emailTemplateTranslations1 = new EmailTemplateTranslations();
                    emailTemplateTranslations1.setName(emailTemplateRequestDto.getName());
                    emailTemplateTranslations1.setSubject(emailTemplateRequestDto.getSubject());
                    emailTemplateTranslations1.setEmailTemplate(emailTemplate);
                    emailTemplateTranslations1.setContext(emailTemplateRequestDto.getContent());
                    emailTemplateTranslations1.setLocale(local);
                    emailTemplateTranslationsRepository.save(emailTemplateTranslations1);
                }

                List<EmailTemplateTranslations> emailTemplateTranslationsList = emailTemplateTranslationsRepository.findByEmailTemplate(emailTemplate);
                emailTemplate.setEmailTemplateTranslations(emailTemplateTranslationsList);
                emailTemplatesRepository.save(emailTemplate);
                EmailTemplateRoponseDto emailTemplateRoponseDto = mailTemplateConverter.converterEmailTemplate(emailTemplate, local);
                return emailTemplateRoponseDto;
            } else {
                throw new NotFoundException("Email Not Found");
            }
        } catch (Exception e) {
            throw new NotFoundException("Email Not Edit");
        }
    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            for (Integer id : ids
            ) {
                EmailTemplate emailTemplate = emailTemplatesRepository.findById(id).get();
                if (emailTemplate == null) {
                    throw new NotFoundException("Not Found Email");
                }
                emailTemplatesRepository.delete(emailTemplate);
            }
            a = true;
        } catch (Exception e) {
            a = false;
        }
        return a;
    }
}
