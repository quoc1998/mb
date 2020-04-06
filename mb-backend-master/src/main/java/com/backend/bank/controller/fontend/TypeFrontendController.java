package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.TypesReponse;
import com.backend.bank.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("{local}/api/fe/type")
public class TypeFrontendController {
    @Autowired
    TypeService typeService;

    @GetMapping
    public List<TypesReponse> findAll() {
        return typeService.findAll();
    }
}
