package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.ExchangeRateDetailRequestDto;
import com.backend.bank.dto.request.ExchangeRateRequestDto;
import com.backend.bank.dto.response.toolbar.ExchangeRateDetailReponseDto;
import com.backend.bank.dto.response.toolbar.ExchangeRateReponseDto;
import com.backend.bank.model.ExchangeRate;
import com.backend.bank.model.ExchangeRateDetail;
import com.backend.bank.repository.ExchangeRateDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ExchangeRateConverter {
    @Autowired
    private ExchangeRateDetailRepository exchangeRateDetailRepository;

    public List<ExchangeRateReponseDto> convertListExchangeRateToDto(List<ExchangeRate> exchangeRates) {
        List<ExchangeRateReponseDto> exchangeRateReponseDtos = new ArrayList<>();
        for (ExchangeRate exchangeRate : exchangeRates) {
            if (!exchangeRateDetailRepository.findByExchangeRate(exchangeRate).isEmpty()) {
                ExchangeRateReponseDto exchangeRateReponseDto = this.convertExchangeRateToDto(exchangeRate);
                exchangeRateReponseDtos.add(exchangeRateReponseDto);
            }
        }
        return exchangeRateReponseDtos;
    }

    public ExchangeRateReponseDto convertExchangeRateToDto(ExchangeRate exchangeRate) {
        ExchangeRateReponseDto exchangeRateReponseDto = new ExchangeRateReponseDto();
        exchangeRateReponseDto.setId(exchangeRate.getId());
        exchangeRateReponseDto.setDate_update(exchangeRate.getDate());
        exchangeRateReponseDto.setCreated_at(exchangeRate.getCreatedAt());
        List<ExchangeRateDetail> exchangeRateDetails = exchangeRateDetailRepository.findByExchangeRate(exchangeRate);
        List<ExchangeRateDetailReponseDto> exchangeRateDetailReponseDtos = new ArrayList<>();
        for (ExchangeRateDetail exchangeRateDetail : exchangeRateDetails) {
            ExchangeRateDetailReponseDto exchangeRateDetailReponseDto = new ExchangeRateDetailReponseDto();
            exchangeRateDetailReponseDto.setId(exchangeRateDetail.getId());
            exchangeRateDetailReponseDto.setCurrency(exchangeRateDetail.getCurrency());
            exchangeRateDetailReponseDto.setBuy_cash(exchangeRateDetail.getBuy_cash());
            exchangeRateDetailReponseDto.setBuy_transfer(exchangeRateDetail.getBuy_transfer());
            exchangeRateDetailReponseDto.setSell(exchangeRateDetail.getSell());
            exchangeRateDetailReponseDto.setChange_USD(exchangeRateDetail.getChange_USD());

            exchangeRateDetailReponseDtos.add(exchangeRateDetailReponseDto);
        }
        exchangeRateReponseDto.setExchangeRateDetail(exchangeRateDetailReponseDtos);
        return exchangeRateReponseDto;
    }

    public ExchangeRate convertDtoToEntity(ExchangeRateRequestDto exchangeRateRequestDto) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setDate(exchangeRateRequestDto.getDate_update());
        exchangeRate.setCreatedAt(exchangeRateRequestDto.getCreatedAt());
        List<ExchangeRateDetail> exchangeRateDetails = new ArrayList<>();
        if (!exchangeRateRequestDto.getExchangeRateDetail().isEmpty()) {
            List<ExchangeRateDetailRequestDto> exchangeRateDetailRequestDtos = exchangeRateRequestDto.getExchangeRateDetail();
            for (ExchangeRateDetailRequestDto exchangeRateDetailRequestDto : exchangeRateDetailRequestDtos) {
                ExchangeRateDetail exchangeRateDetail = new ExchangeRateDetail();
                exchangeRateDetail.setCurrency(exchangeRateDetailRequestDto.getCurrency());
                exchangeRateDetail.setBuy_cash(exchangeRateDetailRequestDto.getBuy_cash());
                exchangeRateDetail.setBuy_transfer(exchangeRateDetailRequestDto.getBuy_transfer());
                exchangeRateDetail.setSell(exchangeRateDetailRequestDto.getSell());
                exchangeRateDetail.setChange_USD(exchangeRateDetailRequestDto.getChange_USD());
                exchangeRateDetail.setExchangeRate(exchangeRate);
                exchangeRateDetails.add(exchangeRateDetail);
            }
        }
        exchangeRate.setExchangeRateDetails(exchangeRateDetails);
        return exchangeRate;
    }

    public ExchangeRate convertDtoToEntityEdit(ExchangeRate exchangeRate, ExchangeRateRequestDto exchangeRateRequestDto) {
        List<ExchangeRateDetail> exchangeRateDetailList = exchangeRateDetailRepository.findByExchangeRate(exchangeRate);
        exchangeRate.setDate(exchangeRateRequestDto.getDate_update());
        if (!exchangeRateRequestDto.getExchangeRateDetail().isEmpty()) {
            List<ExchangeRateDetailRequestDto> exchangeRateDetailRequestDtos = exchangeRateRequestDto.getExchangeRateDetail();
            for (ExchangeRateDetail eRDetail : exchangeRateDetailList) {
                Boolean check = false;
                for (ExchangeRateDetailRequestDto exchangeRateDetailRequestDto : exchangeRateDetailRequestDtos) {
                    if (eRDetail.getCurrency().equals(exchangeRateDetailRequestDto.getCurrency())) {
                        check = true;
                    }
                    Optional<ExchangeRateDetail> exchangeRateDetail = exchangeRateDetailRepository.findByCurrencyAndExchangeRate(exchangeRateDetailRequestDto.getCurrency(), exchangeRate);
                    if (!exchangeRateDetail.isPresent()) {
                        ExchangeRateDetail exchangeRateDetail1 = new ExchangeRateDetail();
                        exchangeRateDetail1.setExchangeRate(exchangeRate);
                        exchangeRateDetail1.setCurrency(exchangeRateDetailRequestDto.getCurrency());
                        exchangeRateDetail1.setBuy_cash(exchangeRateDetailRequestDto.getBuy_cash());
                        exchangeRateDetail1.setBuy_transfer(exchangeRateDetailRequestDto.getBuy_transfer());
                        exchangeRateDetail1.setSell(exchangeRateDetailRequestDto.getSell());
                        exchangeRateDetail1.setChange_USD(exchangeRateDetailRequestDto.getChange_USD());
                        exchangeRateDetailRepository.save(exchangeRateDetail1);
                    } else {
                        exchangeRateDetail.get().setBuy_cash(exchangeRateDetailRequestDto.getBuy_cash());
                        exchangeRateDetail.get().setBuy_transfer(exchangeRateDetailRequestDto.getBuy_transfer());
                        exchangeRateDetail.get().setSell(exchangeRateDetailRequestDto.getSell());
                        exchangeRateDetail.get().setChange_USD(exchangeRateDetailRequestDto.getChange_USD());
                        exchangeRateDetailRepository.save(exchangeRateDetail.get());
                    }
                }
                if (check.equals(false)) {
                    exchangeRateDetailRepository.delete(eRDetail);
                }
            }
        }
        List<ExchangeRateDetail> exchangeRateDetails = exchangeRateDetailRepository.findByExchangeRate(exchangeRate);
        exchangeRate.setExchangeRateDetails(exchangeRateDetails);
        return exchangeRate;
    }
}
