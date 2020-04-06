package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.converters.bases.converter.NetworkConverter;
import com.backend.bank.dto.request.NetworksRequestDto;
import com.backend.bank.dto.response.NetworksReponseDto;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.NetworkTranslations;
import com.backend.bank.model.Networks;
import com.backend.bank.repository.NetworkTranslationsRepository;
import com.backend.bank.repository.NetworksRepository;
import com.backend.bank.service.NetworksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class NetworksServiceImpl implements NetworksService {

    @Autowired
    private NetworksRepository networksRepository;

    @Autowired
    private NetworkConverter networkConverter;

    @Autowired
    private NetworkTranslationsRepository networkTranslationsRepository;

    @Override
    public List<NetworksReponseDto> getNetworks(String locale) {
        Constants.checkLocal(locale);
        List<Networks> networkList = networksRepository.findAll();
        List<NetworksReponseDto> networksReponseDtos = networkConverter.convertListNetworkToDto(locale, networkList);
        return networksReponseDtos;
    }

    @Override
    public List<NetworksReponseDto> searchAll(String locale, String search,String networkCategory,
                                              String districtCity, String provinceCity, Integer status) {
        Constants.checkLocal(locale);
        List<Networks> networkList = networksRepository.searchAll(search, networkCategory, provinceCity, districtCity, status);
        List<NetworksReponseDto> networksResponse = networkConverter.convertListNetworkToDto(locale, networkList);
        return networksResponse;
    }

    @Override
    public NetworksReponseDto addNetwork(String locale, NetworksRequestDto networksRequestDto) {
        Constants.checkLocal(locale);
        NetworksReponseDto networksReponseDto;
        try {
            Networks network = networkConverter.convertNetworkDtoToEntity(locale, networksRequestDto);
            networksRepository.save(network);
            networksReponseDto = networkConverter.convertNetworkToDto(locale, network);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return networksReponseDto;
    }

    @Override
    public NetworksReponseDto getNetworkById(Integer id, String locale) {
        Constants.checkLocal(locale);
        NetworksReponseDto networksReponseDto;
        Optional<Networks> networks = networksRepository.findById(id);
        if (!networks.isPresent()) {
            throw new NotFoundException("Network not found!!");
        }
        networksReponseDto = networkConverter.convertNetworkToDto(locale, networks.get());
        return networksReponseDto;
    }

    @Override
    public NetworksReponseDto editNetwork(Integer id, String locale, NetworksRequestDto networksRequestDto) {
        Constants.checkLocal(locale);
        NetworksReponseDto networksReponseDto;
        Optional<Networks> networks = networksRepository.findById(id);
        if (!networks.isPresent()) {
            throw new NotFoundException("Network not found!!");
        }
        if (networkTranslationsRepository.findByLocaleAndNetworks(locale, networks.get()).isPresent()) {
            try {
                Networks network = networkConverter.convertNetworkDtoToEntityEdit(locale, networks.get(), networksRequestDto);
                networksRepository.save(network);
                networksReponseDto = networkConverter.convertNetworkToDto(locale, network);
                return networksReponseDto;
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void deleteNetwork(Integer id, String locale) {
        Constants.checkLocal(locale);
        Optional<Networks> networks = networksRepository.findById(id);
        if (!networks.isPresent()) {
            throw new NotFoundException("Network not found!!");
        }
        List<NetworkTranslations> networkTranslationsList = networkTranslationsRepository.findByNetworks(networks.get());
        networkTranslationsRepository.deleteInBatch(networkTranslationsList);
        networksRepository.delete(networks.get());
    }

    @Override
    public NetworksReponseDto changeStatusNetwork(Integer id, String locale) {
        Constants.checkLocal(locale);
        Optional<Networks> networks = networksRepository.findById(id);
        Calendar calendar = Calendar.getInstance();
        if (!networks.isPresent()) {
            throw new NotFoundException("Network not found!!");
        }
        if (networks.get().getStatus() == Constants.ACCEPT) {
            networks.get().setStatus(Constants.DENY);
        } else {
            networks.get().setStatus(Constants.ACCEPT);
        }
        networks.get().setUpdatedAt(calendar.getTime());
        networksRepository.save(networks.get());
        return networkConverter.convertNetworkToDto(locale, networks.get());
    }
}
