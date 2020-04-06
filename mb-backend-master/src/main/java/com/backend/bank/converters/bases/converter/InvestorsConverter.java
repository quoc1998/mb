package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.InvestorsRequest;
import com.backend.bank.dto.response.investors.InvestorsResponse;
import com.backend.bank.dto.response.investors.PageInvestorsResponse;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.DetailTypeInvestors;
import com.backend.bank.model.Investors;
import com.backend.bank.model.InvestorsTranslation;
import com.backend.bank.model.TypeInvestors;
import com.backend.bank.repository.DetailTypeInvestorsRepository;
import com.backend.bank.repository.InvestorsRepository;
import com.backend.bank.repository.InvestorsTranslationRepository;
import com.backend.bank.repository.TypeInvestorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InvestorsConverter {
    @Autowired
    TypeInvestorsRepository typeInvestorsRepository;

    @Autowired
    InvestorsRepository investorsRepository;

    @Autowired
    InvestorsTranslationRepository investorsTranslationRepository;

    @Autowired
    DetailTypeInvestorsRepository detailTypeInvestorsRepository;

    public Investors converterRequest(InvestorsRequest investorsRequest) {
        Investors investors = new Investors();
        investors.setNameFile(investorsRequest.getNameFile());
        investors.setCreatedAt(investorsRequest.getCreatedAt());
        investors.setIsActive(investorsRequest.getIsActive());
        List<InvestorsTranslation> investorsTranslations = new ArrayList<>();
        InvestorsTranslation investorsTranslation = new InvestorsTranslation();
        investorsTranslation.setInvestors(investors);
        investorsTranslation.setName(investorsRequest.getName());
        investorsTranslation.setDescription(investorsRequest.getDescription());
        if (investorsRequest.getTypeInvestors() != null) {
            Optional<TypeInvestors> typeInvestors = typeInvestorsRepository.findById(investorsRequest.getTypeInvestors());
            if (!typeInvestors.isPresent()) {
                throw new NotFoundException("Not Found TypeInvestors");
            }
            investorsTranslation.setTypeInvestors(typeInvestors.get());
        }
        if (investorsRequest.getDetailTypeInvestors() != null) {
            Optional<DetailTypeInvestors> detailTypeInvestors = detailTypeInvestorsRepository.findById(investorsRequest.getDetailTypeInvestors());
            if (!detailTypeInvestors.isPresent()) {
                throw new NotFoundException("Not Found TypeInvestors");
            }
            investorsTranslation.setDetailTypeInvestors(detailTypeInvestors.get());
        }
        if (investorsRequest.getUrlVideo()!= null){
            investors.setUrlVideo(investorsRequest.getUrlVideo());
        }
        investorsTranslations.add(investorsTranslation);
        investors.setInvestorsTranslations(investorsTranslations);
        investors.setCreatedAt(investorsRequest.getCreatedAt());
        investors.setUrlFile(investorsRequest.getUrlFile());
        return investors;
    }

    public InvestorsResponse converterResponse(String local, Investors investors) {
        InvestorsResponse investorsResponse = new InvestorsResponse();
        investorsResponse.setIsActive(investors.getIsActive());
        Integer location = checkTranslation(local, investors.getInvestorsTranslations());
        InvestorsTranslation investorsTranslation;
        if (location != -1){
            investorsTranslation = investors.getInvestorsTranslations().get(location);
        }else {
            investorsTranslation = new InvestorsTranslation();
            investorsTranslation.setDescription(investors.getInvestorsTranslations().get(0).getDescription()+local);
            investorsTranslation.setName(investors.getInvestorsTranslations().get(0).getName()+local);
            investorsTranslation.setLocal(local);
            investorsTranslation.setInvestors(investors);
            investorsTranslationRepository.save(investorsTranslation);

        }
        investorsResponse.setName(investorsTranslation.getName());
        investorsResponse.setDescription(investorsTranslation.getDescription());
        if (investors.getUrlVideo()!=null){
            investorsResponse.setUrlVideo(investors.getUrlVideo());
        }
        investorsResponse.setCreatedAt(investors.getCreatedAt());
        investorsResponse.setUrlFile(investors.getUrlFile());
        investorsResponse.setNameFile(investors.getNameFile());
        if (investorsTranslation.getDetailTypeInvestors() != null ){
            investorsResponse.setDetailTypeInvestors(investorsTranslation.getDetailTypeInvestors().getId());
        }
        investorsResponse.setTypeInvestors(investorsTranslation.getTypeInvestors().getId());
        investorsResponse.setId(investors.getId());
        return investorsResponse;
    }

    public List<InvestorsResponse> converterListResponse(String local, List<Investors> investors) {
        List<InvestorsResponse> investorsRespons = new ArrayList<>();
        for (Investors investor : investors
        ) {
            InvestorsResponse investorsResponse = converterResponse(local, investor);
            investorsRespons.add(investorsResponse);
        }
        return investorsRespons;
    }

    public List<PageInvestorsResponse> converterListPageInvestorsResponse(String local, List<Investors> investorsList, List<Integer> years) {
        List<PageInvestorsResponse> investorsResponseList = new ArrayList<>();
        for (Integer year: years
             ) {
            PageInvestorsResponse pageInvestors = new PageInvestorsResponse();
            pageInvestors.setYear(year);
            List<InvestorsResponse> investorsResponses = new ArrayList<>();
            for (Investors investors: investorsList
                 ) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(investors.getCreatedAt());
                if (calendar.get(Calendar.YEAR) == year){
                    InvestorsResponse investorsResponse = this.converterResponse(local, investors);
                    investorsResponses.add(investorsResponse);
                }
            }
            pageInvestors.setInvestors(investorsResponses);
            investorsResponseList.add(pageInvestors);
        }
        return investorsResponseList;
    }

    public List<PageInvestorsResponse> converterListPageInvestorsResponse(String local, List<Investors> investorsList, Integer year) {
        List<PageInvestorsResponse> investorsResponseList = new ArrayList<>();
        PageInvestorsResponse pageInvestors = new PageInvestorsResponse();
        pageInvestors.setYear(year);
        List<InvestorsResponse> investorsResponses = new ArrayList<>();
        for (Investors investors: investorsList
        ) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(investors.getCreatedAt());
            if (calendar.get(Calendar.YEAR) == year){
                InvestorsResponse investorsResponse = this.converterResponse(local, investors);
                investorsResponses.add(investorsResponse);
            }
        }
        pageInvestors.setInvestors(investorsResponses);
        investorsResponseList.add(pageInvestors);
        return investorsResponseList;
    }

    public static Integer checkTranslation(String local, List<InvestorsTranslation> investorsTranslations){
        Integer size = investorsTranslations.size();
        if (size == 0){
            throw new NotFoundException("Investors not translation");
        }
        for (int index = 0; index< size; index++){
            if (investorsTranslations.get(index).getLocal().equalsIgnoreCase(local)){
                return index;
            }
        }
        return -1;
    }
}
