package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.ProvincesConverter;
import com.backend.bank.dto.response.ProvincesResponseDto;
import com.backend.bank.repository.ProvincesRepository;
import com.backend.bank.service.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvincesServiceImpl implements ProvincesService {
    @Autowired
    private ProvincesRepository provincesRepository;

    @Autowired
    private ProvincesConverter provincesConverter;

    public List<ProvincesResponseDto> getAll() {
        return provincesConverter.convertListProvinces(provincesRepository.findAll());
    }
}
