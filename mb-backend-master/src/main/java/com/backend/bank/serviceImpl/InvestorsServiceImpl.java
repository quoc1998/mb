package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.InvestorsConverter;
import com.backend.bank.dto.request.InvestorsRequest;
import com.backend.bank.dto.request.InvestorsSearchRequest;
import com.backend.bank.dto.response.investors.InvestorsResponse;
import com.backend.bank.dto.response.investors.PageInvestorsResponse;
import com.backend.bank.dto.response.investors.PaginationInvestors;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.DetailTypeInvestors;
import com.backend.bank.model.Investors;
import com.backend.bank.model.TypeInvestors;
import com.backend.bank.repository.DetailTypeInvestorsRepository;
import com.backend.bank.repository.InvestorsRepository;
import com.backend.bank.repository.TypeInvestorsRepository;
import com.backend.bank.service.InvestorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InvestorsServiceImpl implements InvestorsService {
    @Autowired
    InvestorsRepository investorsRepository;

    @Autowired
    InvestorsConverter investorsConverter;

    @Autowired
    TypeInvestorsRepository typeInvestorsRepository;

    @Autowired
    DetailTypeInvestorsRepository detailTypeInvestorsRepository;

    @Override
    public List<InvestorsResponse> findAll(String local) {
        List<Investors> investors = investorsRepository.findAll();
        return investorsConverter.converterListResponse(local, investors);
    }

    @Override
    public InvestorsResponse findById(String local, Integer id) {
        Optional<Investors> investors = investorsRepository.findById(id);
        if (!investors.isPresent()) {
            throw new NotFoundException("Not Found Regulation");
        }
        return investorsConverter.converterResponse(local,investors.get());
    }

    @Override
    public Boolean addRegulation(String local, InvestorsRequest investorsRequest) {
        try {
            Investors investors = investorsConverter.converterRequest(investorsRequest);
            investorsRepository.save(investors);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean editRegulation(String local, Integer id, InvestorsRequest investorsRequest) {
        try {
            Optional<Investors> investors = investorsRepository.findById(id);
            if (!investors.isPresent()) {
                throw new NotFoundException("Not Found Regulation");
            }
            Integer location = InvestorsConverter.checkTranslation(local, investors.get().getInvestorsTranslations());

            investors.get().getInvestorsTranslations().get(location).setDescription(investorsRequest.getDescription());
            if (investorsRequest.getUrlVideo() != null) {
                investors.get().setUrlVideo(investorsRequest.getUrlVideo());
            }
            investors.get().setCreatedAt(investorsRequest.getCreatedAt());
            investors.get().setUpdatedAt(new Date());
            investors.get().setNameFile(investorsRequest.getNameFile());
            investors.get().setIsActive(investorsRequest.getIsActive());
            investors.get().getInvestorsTranslations().get(location).setName(investorsRequest.getName());
            investors.get().setUrlFile(investorsRequest.getUrlFile());
            if (investorsRequest.getTypeInvestors() != null) {
                Optional<TypeInvestors> typeInvestors = typeInvestorsRepository.findById(investorsRequest.getTypeInvestors());
                if (!typeInvestors.isPresent()) {
                    throw new NotFoundException("Not Found TypeInvestors");
                }
                investors.get().getInvestorsTranslations().get(location).setTypeInvestors(typeInvestors.get());
            }
            if (investorsRequest.getTypeInvestors() != null) {
                Optional<DetailTypeInvestors> detailTypeInvestors =
                        detailTypeInvestorsRepository.findById(investorsRequest.getDetailTypeInvestors());
                if (!detailTypeInvestors.isPresent()) {
                    throw new NotFoundException("Not Found TypeInvestors");
                }
                investors.get().getInvestorsTranslations().get(location).setDetailTypeInvestors(detailTypeInvestors.get());
            }
            investorsRepository.save(investors.get());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteRegulation(List<Integer> ids) {
        try {
            for (Integer id : ids
            ) {
                Optional<Investors> regulation = investorsRepository.findById(id);
                if (!regulation.isPresent()) {
                    throw new NotFoundException("Not Found Regulation");
                }
                investorsRepository.delete(regulation.get());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public InvestorsResponse activeRegulation(String local, Integer id) {
        Optional<Investors> investors = investorsRepository.findById(id);
        if (!investors.isPresent()) {
            throw new NotFoundException("Not Found Regulation");
        }
        investors.get().setIsActive(true);
        investorsRepository.save(investors.get());
        return investorsConverter.converterResponse(local, investors.get());
    }

    @Override
    public List<InvestorsResponse> findAllIsActive(String local ) {
        List<Investors> investors = investorsRepository.findAllByIsActive(true);
        return investorsConverter.converterListResponse(local, investors);
    }

    @Override
    public List<InvestorsResponse> findAllNotIsActive(String local) {
        List<Investors> investors = investorsRepository.findAllByIsActive(false);
        return investorsConverter.converterListResponse(local, investors);
    }

    @Override
    public List<InvestorsResponse> findAllTypeAndYearAndIsActive(String local, InvestorsSearchRequest investorsSearchRequest) {
        List<Investors> investors = investorsRepository
                .findAllByYearAndIsActiveAndTypeInvestorsId(investorsSearchRequest.getIdSearch()
                        , investorsSearchRequest.getYear(), 1);
        return investorsConverter.converterListResponse(local, investors);
    }

    @Override
    public List<InvestorsResponse> findAllYearAndIsActive(String local, Integer year) {
        List<Investors> investors = investorsRepository.findAllByYearAndIsActive(year, true);
        return investorsConverter.converterListResponse(local, investors);
    }

    @Override
    public InvestorsResponse findAllYearAndIsActiveAndSort(String local, Integer year) {
        Investors investors = investorsRepository.findAllByYearAndIsActiveAndSort(year, true);
        if (investors == null) {
            throw new NotFoundException("Not Found Investors");
        }
        return investorsConverter.converterResponse(local, investors);
    }

    @Override
    public PaginationInvestors findAllByTypeIsActive(String local, Integer type, Integer year, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Investors> pageAll;
        if (year == null ){
            pageAll = investorsRepository.findAllByInvestorsId(pageable, type, true);
        }else {
            pageAll = investorsRepository.findAllByTypeIsActive(pageable, type, year, true);
        }
        List<InvestorsResponse> pagesResponse = investorsConverter.converterListResponse(local, pageAll.getContent());
        PaginationInvestors paginationBlock = new PaginationInvestors();
        paginationBlock.setInvestors(pagesResponse);
        paginationBlock.setSize(pageAll.getTotalPages());
        return paginationBlock;
    }

    @Override
    public List<TypeInvestors> findAllTypeInvestors(String local) {
        return typeInvestorsRepository.findAll();
    }

    @Override
    public List<DetailTypeInvestors> findAllDetailTypeInvestors(String local, Integer id) {
        return detailTypeInvestorsRepository.findAllByTypeInvestorsId(id);
    }

    @Override
    public List<PageInvestorsResponse> findAllByInvestorsId(String local, Integer typeInvestorsId, Integer detailTypeId, Integer year,
                                                            Integer page, Integer number) {
        page = page == 1 ? 0 : page;
        String detailTypeId1 = detailTypeId == 0 ? "" : detailTypeId.toString();
        List<Investors> investors;
        if (year == 0){
            if (typeInvestorsId == 9){
                investors = investorsRepository.findAllByInvestorsId(typeInvestorsId,  true);
            }else {
                investors = investorsRepository.findAllByInvestorsId(typeInvestorsId, detailTypeId1,  true);
            }
            List<Integer> yearsList = investorsRepository.getAllYear(page, number);
            return investorsConverter.converterListPageInvestorsResponse(local, investors, yearsList);
        }else {
            if (typeInvestorsId == 9){
                investors = investorsRepository.findAllByInvestorsId(typeInvestorsId,  true);
            }else {
                investors = investorsRepository.findAllByInvestorsId(typeInvestorsId, detailTypeId1,  true);
            }return investorsConverter.converterListPageInvestorsResponse(local, investors, year);
        }
    }


}
