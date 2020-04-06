package com.backend.bank.service;

import com.backend.bank.dto.request.EmailTemplateRequestDto;
import com.backend.bank.dto.response.setting.EmailTemplateRoponseDto;
import com.backend.bank.dto.response.setting.PaginationEmailTemplate;

import java.util.List;

public interface EmailTemplatesService {
    List<EmailTemplateRoponseDto> findAll(String locale);
    PaginationEmailTemplate searchMail(String locale, String search, Integer page,Integer number);
    EmailTemplateRoponseDto findById(String locale, Integer id);
    Boolean deleteById(String locale, Integer id);
    EmailTemplateRoponseDto addEmailTemplate(String locale, EmailTemplateRequestDto emailTemplateRequestDto);
    EmailTemplateRoponseDto editEmailTemplate(String locale, Integer idMail,EmailTemplateRequestDto emailTemplateRequestDto);
    Boolean deleteIds(List<Integer> ids);

}
