package com.backend.bank.service;

import com.backend.bank.dto.response.DistrictsResponseDto;
import com.backend.bank.model.Districts;
import org.springframework.stereotype.Component;


import java.util.List;
@Component
public interface DistrictsService {
    List<DistrictsResponseDto> getAll();
    List<DistrictsResponseDto> getAllByProvince(int idProvince);
}
