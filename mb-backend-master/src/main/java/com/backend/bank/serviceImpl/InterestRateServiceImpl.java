package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.converters.bases.converter.InterestRateConverter;
import com.backend.bank.dto.request.InterestRateRequestDto;
import com.backend.bank.dto.response.toolbar.InterestRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationInterestRate;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.InterestRate;
import com.backend.bank.model.InterestRateTranslations;
import com.backend.bank.repository.InterestRateRepository;
import com.backend.bank.repository.InterestRateTranslationsRepository;
import com.backend.bank.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestRateServiceImpl implements InterestRateService {

    @Autowired
    private InterestRateRepository interestRateRepository;

    @Autowired
    private InterestRateConverter interestRateConverter;

    @Autowired
    private InterestRateTranslationsRepository interestRateTranslationsRepository;

    @Override
    public List<InterestRateReponseDto> getAll(String locale) {
        Constants.checkLocal(locale);
        List<InterestRate> interestRates = interestRateRepository.findAll();
        List<InterestRateReponseDto> interestRateReponseDtos = interestRateConverter.convertListInterestRateToDto(locale, interestRates);
        return interestRateReponseDtos;
    }

    @Override
    public PaginationInterestRate findAllPagin(String locale, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<InterestRate> pageAll = interestRateRepository.getAllBy(pageable);
        List<InterestRateReponseDto> exchangeRateReponseDtos = interestRateConverter.convertListInterestRateToDto(locale, pageAll.getContent());
        PaginationInterestRate paginationExchangeRate = new PaginationInterestRate();
        paginationExchangeRate.setInterestRates(exchangeRateReponseDtos);
        paginationExchangeRate.setSize(pageAll.getTotalPages());
        return paginationExchangeRate;
    }

    @Override
    public InterestRateReponseDto addInterestRate(String locale, InterestRateRequestDto interestRateRequestDto) {
        Constants.checkLocal(locale);
        InterestRateReponseDto interestRateReponseDto;
        try {
            InterestRate interestRate = interestRateConverter.convertInterestRateDtoToEntity(locale, interestRateRequestDto);
            interestRateRepository.save(interestRate);
            interestRateReponseDto = interestRateConverter.convertInterestRateToDto(locale, interestRate);
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
        return interestRateReponseDto;
    }

    @Override
    public InterestRateReponseDto getInterestRate(String locale, Integer id) {
        Constants.checkLocal(locale);
        InterestRateReponseDto interestRateReponseDto;
        Optional<InterestRate> interestRate = interestRateRepository.findById(id);
        if (!interestRate.isPresent()) {
            throw new NotFoundException("InterestRate not found!!");
        }
        interestRateReponseDto = interestRateConverter.convertInterestRateToDto(locale, interestRate.get());
        return interestRateReponseDto;
    }

    @Override
    public InterestRateReponseDto editInterestRate(String locale, Integer id, InterestRateRequestDto interestRateRequestDto) {
        Constants.checkLocal(locale);
        InterestRateReponseDto interestRateReponseDto;
        Optional<InterestRate> interestRate = interestRateRepository.findById(id);
        if (!interestRate.isPresent()) {
            throw new NotFoundException("InterestRate not found!!");
        }
        if (interestRateTranslationsRepository.findByLocaleAndInterestRate(locale, interestRate.get()).isPresent()) {
            try {
                InterestRate interestRate1 = interestRateConverter.convertInterestRateDtoToEntityEdit(locale, interestRate.get(), interestRateRequestDto);
                interestRateRepository.save(interestRate1);
                interestRateReponseDto = interestRateConverter.convertInterestRateToDto(locale, interestRate1);
                return interestRateReponseDto;
            } catch (Exception e) {
                throw new BadRequestException(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void deleteInterestRate(String locale, Integer id) {
        Constants.checkLocal(locale);
        Optional<InterestRate> interestRate = interestRateRepository.findById(id);
        if (!interestRate.isPresent()) {
            throw new NotFoundException("InterestRate not found!!");
        }
        List<InterestRateTranslations> interestRateTranslationsList = interestRateTranslationsRepository.findByInterestRate(interestRate.get());
        interestRateTranslationsRepository.deleteInBatch(interestRateTranslationsList);
        interestRateRepository.delete(interestRate.get());
    }
}
