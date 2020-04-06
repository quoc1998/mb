package com.backend.bank.controller.fontend;


import com.backend.bank.dto.response.ProvincesResponseDto;
import com.backend.bank.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fe/province")
public class ProvinceFrontendController {
    @Autowired
    private ProvincesService provincesService;

    @GetMapping
    public List<ProvincesResponseDto> getAllProvinces() {
        return provincesService.getAll();
    }
}
