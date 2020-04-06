package com.backend.bank.controller;


import com.backend.bank.dto.response.ProvincesResponseDto;
import com.backend.bank.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinceController {
    @Autowired
    private ProvincesService provincesService;

    @GetMapping
    public List<ProvincesResponseDto> getAllProvinces() {
        return provincesService.getAll();
    }
}
