package com.backend.bank.controller.fontend;

import com.backend.bank.dto.request.EmailTemplateRequestDto;
import com.backend.bank.dto.response.setting.EmailTemplateRoponseDto;
import com.backend.bank.dto.response.setting.PaginationEmailTemplate;
import com.backend.bank.service.EmailTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/fe/email")
public class MailTemplatesFrontendController {
    @Autowired
    EmailTemplatesService emailTemplatesService;

    @GetMapping()
    public List<EmailTemplateRoponseDto> findAll(@PathVariable("local") String local) {
        return emailTemplatesService.findAll(local);
    }

    @GetMapping("/{id}")
    public EmailTemplateRoponseDto findById(@PathVariable("local") String local, @PathVariable("id") Integer idMail) {
        return emailTemplatesService.findById(local, idMail);
    }

}
