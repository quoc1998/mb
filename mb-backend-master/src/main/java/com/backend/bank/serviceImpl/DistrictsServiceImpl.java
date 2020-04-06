package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.DistrictConverter;
import com.backend.bank.dto.response.DistrictsResponseDto;
import com.backend.bank.model.Provinces;
import com.backend.bank.repository.DistrictsRepository;
import com.backend.bank.service.DistrictsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictsServiceImpl implements DistrictsService {
    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private DistrictConverter districtConverter;

    @Override
    public List<DistrictsResponseDto> getAll() {
        return districtConverter.convertListDistrict(districtsRepository.findAll());
    }

    @Override
    public List<DistrictsResponseDto> getAllByProvince(int idProvince) {
        return districtConverter.convertListDistrict(districtsRepository.findByProvinces(idProvince));
    }
}
