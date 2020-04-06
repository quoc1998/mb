package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.response.DistrictsResponseDto;
import com.backend.bank.model.Districts;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DistrictConverter {
    public DistrictsResponseDto convertDistrictToDto(Districts districts) {
        DistrictsResponseDto districtsResponseDto = new DistrictsResponseDto();
        districtsResponseDto.setId(districts.getId());
        districtsResponseDto.setName(districts.getName());
        return districtsResponseDto;
    }

    public List<DistrictsResponseDto> convertListDistrict(List<Districts> districtsList) {
        List<DistrictsResponseDto> districtsResponseDtos = new ArrayList<>();
        for (Districts district : districtsList) {
            districtsResponseDtos.add(this.convertDistrictToDto(district));
        }
        return districtsResponseDtos;
    }
}
