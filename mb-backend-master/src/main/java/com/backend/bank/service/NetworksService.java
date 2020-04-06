package com.backend.bank.service;

import com.backend.bank.dto.request.NetworksRequestDto;
import com.backend.bank.dto.response.NetworksReponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NetworksService {
    List<NetworksReponseDto> getNetworks(String locale);

    List<NetworksReponseDto> searchAll(String locale, String search,String networkCategory, String districtCity, String provinceCity, Integer status);

    NetworksReponseDto getNetworkById(Integer id, String locale);

    NetworksReponseDto addNetwork(String locale, NetworksRequestDto networksRequestDto);

    NetworksReponseDto editNetwork(Integer id, String locale, NetworksRequestDto networksRequestDto);

    void deleteNetwork(Integer id, String locale);

    NetworksReponseDto changeStatusNetwork(Integer id, String locale);

}
