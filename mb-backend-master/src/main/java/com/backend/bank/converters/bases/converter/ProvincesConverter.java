package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.response.ProvincesResponseDto;
import com.backend.bank.model.Provinces;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProvincesConverter {
    public ProvincesResponseDto convertProvinceToDto(Provinces provinces) {
        ProvincesResponseDto provincesResponseDto = new ProvincesResponseDto();
        provincesResponseDto.setId(provinces.getId());
        provincesResponseDto.setName(provinces.getName());
        return provincesResponseDto;
    }

    public List<ProvincesResponseDto> convertListProvinces(List<Provinces> provincesList) {
        List<ProvincesResponseDto> provincesResponseDtos = new ArrayList<>();
        for (Provinces province : provincesList) {
            provincesResponseDtos.add(this.convertProvinceToDto(province));
        }
        return provincesResponseDtos;
    }

}
