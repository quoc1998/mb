package com.backend.bank.controller.fontend;

import com.backend.bank.controller.NewsController;
import com.backend.bank.dto.request.SendFormRequestDto;
import com.backend.bank.dto.response.form.FormResponseDto;
import com.backend.bank.service.FormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{local}/api/fe/forms")
public class FormFrontendController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    FormService formService;

    @GetMapping("/{id}")
    public FormResponseDto findById(@PathVariable("id") Integer id, @PathVariable("local") String local) {
        return formService.findById(id, local);
    }

    @PostMapping("/send")
    public Boolean sendMail(@RequestBody SendFormRequestDto sendFormRequestDto, @PathVariable("local") String local) {
        return formService.sendMail(local, sendFormRequestDto);
    }




}
