package com.backend.bank.controller;

import com.backend.bank.dto.response.DistrictsResponseDto;
import com.backend.bank.service.DistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/district")
public class DistrictController {
    @Autowired
    private DistrictsService districtsService;

    @GetMapping()
    public List<DistrictsResponseDto> getAll() {
        return districtsService.getAll();
    }

    @GetMapping("/{idProvince}")
    public List<DistrictsResponseDto> getAllProvinces(@PathVariable("idProvince") int idProvince) {
        return districtsService.getAllByProvince(idProvince);
    }
}
