package com.backend.bank.controller;

import com.backend.bank.dto.request.EmailTemplateRequestDto;
import com.backend.bank.dto.response.setting.EmailTemplateRoponseDto;
import com.backend.bank.dto.response.setting.PaginationEmailTemplate;
import com.backend.bank.service.EmailTemplatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/email")
public class MailTemplatesController {
    @Autowired
    EmailTemplatesService emailTemplatesService;

    @Secured({"ROLE_GET MAILTEMPLATE", "ROLE_XEM MAILTEMPLATE"})
    @GetMapping()
    public List<EmailTemplateRoponseDto> findAll(@PathVariable("local") String local) {
        return emailTemplatesService.findAll(local);
    }

    @Secured({"ROLE_CREATE MAILTEMPLATE", "ROLE_TẠO MAILTEMPLATE"})
    @PostMapping()
    public EmailTemplateRoponseDto addEmailTemplate(@PathVariable("local") String local,
                                                    @RequestBody EmailTemplateRequestDto emailTemplateRequestDto) {
        return emailTemplatesService.addEmailTemplate(local, emailTemplateRequestDto);
    }

    @GetMapping("/{id}")
    public EmailTemplateRoponseDto findById(@PathVariable("local") String local, @PathVariable("id") Integer idMail) {
        return emailTemplatesService.findById(local, idMail);
    }

    @Secured({"ROLE_EDIT MAILTEMPLATE", "ROLE_CHỈNH SỬA MAILTEMPLATE"})
    @PutMapping("/{id}")
    public EmailTemplateRoponseDto editEmailTemplate(@PathVariable("local") String local, @PathVariable("id") Integer id,
                                                     @RequestBody EmailTemplateRequestDto emailTemplateRequestDto
    ) {
        return emailTemplatesService.editEmailTemplate(local, id, emailTemplateRequestDto);
    }


    @Secured({"ROLE_DELETE MAILTEMPLATE", "ROLE_XÓA MAILTEMPLATE"})
    @DeleteMapping("/{id}")
    public Boolean deleteEmailTemplate(@PathVariable("local") String local, @PathVariable("id") Integer id) {
        return emailTemplatesService.deleteById(local, id);
    }

    @Secured({"ROLE_DELETE MAILTEMPLATE", "ROLE_XÓA MAILTEMPLATE"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListMail(@RequestBody List<Integer> ids) {
        return emailTemplatesService.deleteIds(ids);
    }


    @Secured({"ROLE_GET MAILTEMPLATE", "ROLE_XEM MAILTEMPLATE"})
    @GetMapping("/search")
    public PaginationEmailTemplate searchMail(@PathVariable("local") String local,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                              @RequestParam(name = "search", required = false, defaultValue = "")
                                                      String search) {
        return emailTemplatesService.searchMail(local, search, page, number);
    }
}
