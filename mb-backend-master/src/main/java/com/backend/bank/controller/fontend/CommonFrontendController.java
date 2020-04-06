package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.setting.CommonReponseDto;
import com.backend.bank.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("{locale}/api/fe/common")
public class CommonFrontendController {
    @Autowired
    CommonService commonService;

    @GetMapping("/{name}")
    public CommonReponseDto getLogo(@PathVariable("name") String name, @PathVariable("locale") String locale) throws IOException {
        return commonService.findByName(name, locale);
    }



}
