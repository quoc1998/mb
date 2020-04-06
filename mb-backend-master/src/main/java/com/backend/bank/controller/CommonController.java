package com.backend.bank.controller;

import com.backend.bank.dto.request.GeneralStorefontRequestDto;
import com.backend.bank.dto.request.LinkAppRequestDto;
import com.backend.bank.dto.request.LogoStorefontRequestDto;
import com.backend.bank.dto.request.SocialLinkRequestDto;
import com.backend.bank.dto.response.setting.*;
import com.backend.bank.service.CommonService;
import com.backend.bank.serviceImpl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("{locale}/api/common")
public class CommonController {
    @Autowired
    CommonService commonService;

    @Autowired
    private FileStorageService fileStorageService;

    @Secured({"ROLE_GET STORE", "ROLE_XEM STORE"})
    @GetMapping("/{name}")
    public CommonReponseDto getLogo(@PathVariable("name") String name, @PathVariable("locale") String locale) throws IOException {
        return commonService.findByName(name, locale);
    }

    @Secured({"ROLE_EDIT STORE", "ROLE_SỬA STORE"})
    @PutMapping("/edit/logo")
    public LogoReponseDto editLogo(@PathVariable("locale") String locale,
                                   @RequestBody LogoStorefontRequestDto commonRequestDto) {

        return commonService.editLogo(locale, "logo", commonRequestDto);

    }

    @Secured({"ROLE_EDIT STORE", "ROLE_SỬA STORE"})
    @PutMapping("/edit/general")
    public GeneralReponseDto editGeneral(@PathVariable("locale") String locale,
                                         @RequestBody GeneralStorefontRequestDto commonRequestDto) {

        return commonService.editGeneral(locale, "general", commonRequestDto);

    }

    @Secured({"ROLE_EDIT STORE", "ROLE_SỬA STORE"})
    @PutMapping("/edit/sociallink")
    public SocialLinkReponseDto editSocial(@PathVariable("locale") String locale,
                                           @RequestBody SocialLinkRequestDto commonRequestDto) {
        return commonService.editSocialLink(locale, "sociallink", commonRequestDto);

    }

    @Secured({"ROLE_EDIT STORE", "ROLE_SỬA STORE"})
    @PutMapping("/edit/linkapp")
    public LinkAppResponseDto editLinkApp(@PathVariable("locale") String locale,
                                         @RequestBody LinkAppRequestDto commonRequestDto) {
        return commonService.editLinkApp(locale, "linkapp", commonRequestDto);

    }

}
