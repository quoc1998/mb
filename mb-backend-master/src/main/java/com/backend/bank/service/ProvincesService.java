package com.backend.bank.service;

import com.backend.bank.dto.response.ProvincesResponseDto;
import com.backend.bank.model.Provinces;
import org.springframework.stereotype.Component;


import java.util.List;
@Component
public interface ProvincesService {
    List<ProvincesResponseDto> getAll();
}
