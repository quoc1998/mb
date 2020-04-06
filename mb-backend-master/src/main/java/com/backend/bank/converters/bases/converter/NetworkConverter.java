package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.NetworksRequestDto;
import com.backend.bank.dto.response.NetworksReponseDto;
import com.backend.bank.model.Networks;
import com.backend.bank.model.NetworkTranslations;
import com.backend.bank.repository.NetworkTranslationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class NetworkConverter {
    @Autowired
    NetworkTranslationsRepository networkTranslationsRepository;

    private Calendar calendar = Calendar.getInstance();

    public List<NetworksReponseDto> convertListNetworkToDto(String locale, List<Networks> networks) {
        List<NetworksReponseDto> networksReponseDtos = new ArrayList<>();
        for (Networks network : networks) {
            NetworksReponseDto networksReponseDto = this.convertNetworkToDto(locale, network);
            networksReponseDtos.add(networksReponseDto);
        }
        return networksReponseDtos;

    }

    public Networks convertNetworkDtoToEntity(String locale, NetworksRequestDto networksRequestDto) {
        Networks network = new Networks();
        network.setLongitude(networksRequestDto.getLongitude());
        network.setLatitude(networksRequestDto.getLatitude());
        network.setCreatedAt(calendar.getTime());

        NetworkTranslations networkTranslations = new NetworkTranslations();
        networkTranslations.setNetwork_category(networksRequestDto.getNetwork_category());
        networkTranslations.setAddress_name(networksRequestDto.getAddress_name());
        networkTranslations.setAddress(networksRequestDto.getAddress());
        networkTranslations.setLocale(locale);
//        networkTranslations.setLanguage(networksRequestDto.getLanguage());
        networkTranslations.setProvince_city(networksRequestDto.getProvince_city());
        networkTranslations.setDistrict_city(networksRequestDto.getDistrict_city());
        networkTranslations.setDescription(networksRequestDto.getDescription());
        networkTranslations.setNetworks(network);

        List<NetworkTranslations> translationsList = new ArrayList<>();
        translationsList.add(networkTranslations);

        network.setNetworkTranslations(translationsList);
        return network;
    }

    public NetworksReponseDto convertNetworkToDto(String locale, Networks networks) {
        NetworksReponseDto networksReponseDto = new NetworksReponseDto();
        networksReponseDto.setId(networks.getId());
        networksReponseDto.setLongitude(networks.getLongitude());
        networksReponseDto.setLatitude(networks.getLatitude());
        networksReponseDto.setStatus(networks.getStatus());
        networksReponseDto.setCreated_at(networks.getCreatedAt());
        networksReponseDto.setUpdated_at(networks.getUpdatedAt());

        NetworkTranslations networkTranslations = networkTranslationsRepository.findByLocaleAndNetworks(locale, networks).orElse(null);
        if (networkTranslations == null) {
            networksReponseDto.setLocale(locale);
            networksReponseDto.setAddress_name("");
            networksReponseDto.setAddress("");
            networksReponseDto.setDescription("");
            networksReponseDto.setNetwork_category("");

        } else {
            networksReponseDto.setLocale(networkTranslations.getLocale());
            networksReponseDto.setAddress_name(networkTranslations.getAddress_name());
            networksReponseDto.setAddress(networkTranslations.getAddress());
            networksReponseDto.setDescription(networkTranslations.getDescription());
            networksReponseDto.setNetwork_category(networkTranslations.getNetwork_category());
            networksReponseDto.setProvince_city(networkTranslations.getProvince_city());
            networksReponseDto.setDistrict_city(networkTranslations.getDistrict_city());
        }
        return networksReponseDto;
    }

    public Networks convertNetworkDtoToEntityEdit(String locale, Networks network, NetworksRequestDto networksRequestDto) {
        network.setLongitude(networksRequestDto.getLongitude());
        network.setLatitude(networksRequestDto.getLatitude());
        network.setUpdatedAt(calendar.getTime());

        NetworkTranslations networkTranslations = networkTranslationsRepository.findByLocaleAndNetworks(locale, network).orElse(null);
        if (networkTranslations != null) {
            networkTranslations.setNetwork_category(networksRequestDto.getNetwork_category());
            networkTranslations.setAddress_name(networksRequestDto.getAddress_name());
            networkTranslations.setAddress(networksRequestDto.getAddress());
            networkTranslations.setProvince_city(networksRequestDto.getProvince_city());
            networkTranslations.setDistrict_city(networksRequestDto.getDistrict_city());
            networkTranslations.setDescription(networksRequestDto.getDescription());
            networkTranslationsRepository.save(networkTranslations);
        } else {
            NetworkTranslations networkTranslations1 = new NetworkTranslations();
            networkTranslations1.setNetworks(network);
            networkTranslations1.setLocale(locale);
            networkTranslations1.setNetwork_category(networksRequestDto.getNetwork_category());
            networkTranslations1.setAddress_name(networksRequestDto.getAddress_name());
            networkTranslations1.setAddress(networksRequestDto.getAddress());
            networkTranslations1.setProvince_city(networksRequestDto.getProvince_city());
            networkTranslations1.setDistrict_city(networksRequestDto.getDistrict_city());
            networkTranslations1.setDescription(networksRequestDto.getDescription());
            networkTranslationsRepository.save(networkTranslations1);
        }

        List<NetworkTranslations> translationsList = networkTranslationsRepository.findByNetworks(network);

        network.setNetworkTranslations(translationsList);
        return network;
    }
}
