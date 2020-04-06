package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.InterestRateRequestDto;
import com.backend.bank.dto.response.toolbar.InterestRateReponseDto;
import com.backend.bank.model.InterestRate;
import com.backend.bank.model.InterestRateTranslations;
import com.backend.bank.repository.InterestRateTranslationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Component
public class InterestRateConverter {
    @Autowired
    private InterestRateTranslationsRepository interestRateTranslationsRepository;

    private Calendar calendar = Calendar.getInstance();

    public List<InterestRateReponseDto> convertListInterestRateToDto(String locale, List<InterestRate> interestRates) {
        List<InterestRateReponseDto> interestRateReponseDtos = new ArrayList<>();
        for (InterestRate interestRate : interestRates) {
            InterestRateReponseDto interestRateReponseDto = this.convertInterestRateToDto(locale, interestRate);
            interestRateReponseDtos.add(interestRateReponseDto);
        }
        return interestRateReponseDtos;
    }

    public InterestRateReponseDto convertInterestRateToDto(String locale, InterestRate interestRate) {
        InterestRateReponseDto interestRateReponseDto = new InterestRateReponseDto();
        interestRateReponseDto.setId(interestRate.getId());
        interestRateReponseDto.setTerm(interestRate.getTerm());
        interestRateReponseDto.setInterest_rate(interestRate.getInterest_rate());
        interestRateReponseDto.setCreated_at(interestRate.getCreatedAt());
        interestRateReponseDto.setUpdated_at(interestRate.getUpdatedAt());

        InterestRateTranslations interestRateTranslations = interestRateTranslationsRepository.findByLocaleAndInterestRate(locale, interestRate).orElse(null);

        if (interestRateTranslations == null) {
            interestRateReponseDto.setDescription(null);
            interestRateReponseDto.setLocale(locale);
        } else {
            interestRateReponseDto.setLocale(interestRateTranslations.getLocale());
            interestRateReponseDto.setDescription(interestRateTranslations.getDescription());
        }

        return interestRateReponseDto;
    }

    public InterestRate convertInterestRateDtoToEntity(String locale, InterestRateRequestDto interestRateRequestDto) {
        InterestRate interestRate = new InterestRate();
        interestRate.setTerm(interestRateRequestDto.getTerm());
        interestRate.setInterest_rate(interestRateRequestDto.getInterest_rate());
        interestRate.setCreatedAt(calendar.getTime());

        InterestRateTranslations interestRateTranslations = new InterestRateTranslations();
        interestRateTranslations.setLocale(locale);
        interestRateTranslations.setDescription(interestRateRequestDto.getDescription());
        interestRateTranslations.setInterestRate(interestRate);

        List<InterestRateTranslations> interestRateTranslationsList = new ArrayList<>();
        interestRateTranslationsList.add(interestRateTranslations);

        interestRate.setInterestRateTranslations(interestRateTranslationsList);
        return interestRate;
    }

    public InterestRate convertInterestRateDtoToEntityEdit(String locale, InterestRate interestRate, InterestRateRequestDto interestRateRequestDto) {
        interestRate.setTerm(interestRateRequestDto.getTerm());
        interestRate.setInterest_rate(interestRateRequestDto.getInterest_rate());
        interestRate.setUpdatedAt(calendar.getTime());

        Optional<InterestRateTranslations> interestRateTranslations = interestRateTranslationsRepository.findByLocaleAndInterestRate(locale, interestRate);

        if (interestRateTranslations.isPresent()) {
            interestRateTranslations.get().setDescription(interestRateRequestDto.getDescription());
            interestRateTranslationsRepository.save(interestRateTranslations.get());
        } else {
            InterestRateTranslations interestRateTranslations1 = new InterestRateTranslations();
            interestRateTranslations1.setLocale(locale);
            interestRateTranslations1.setDescription(interestRateRequestDto.getDescription());
            interestRateTranslations1.setInterestRate(interestRate);
            interestRateTranslationsRepository.save(interestRateTranslations1);
        }

        List<InterestRateTranslations> interestRateTranslationsList = interestRateTranslationsRepository.findByInterestRate(interestRate);

        interestRate.setInterestRateTranslations(interestRateTranslationsList);
        return interestRate;
    }
}
