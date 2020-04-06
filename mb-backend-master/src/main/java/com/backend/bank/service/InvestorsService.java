package com.backend.bank.service;

import com.backend.bank.dto.request.InvestorsRequest;
import com.backend.bank.dto.request.InvestorsSearchRequest;
import com.backend.bank.dto.response.investors.InvestorsResponse;
import com.backend.bank.dto.response.investors.PageInvestorsResponse;
import com.backend.bank.dto.response.investors.PaginationInvestors;
import com.backend.bank.model.DetailTypeInvestors;
import com.backend.bank.model.TypeInvestors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvestorsService {
    List<InvestorsResponse> findAll(String local);

    InvestorsResponse findById(String local, Integer id);

    Boolean addRegulation(String local, InvestorsRequest investorsRequest);

    Boolean editRegulation(String local, Integer id, InvestorsRequest investorsRequest);

    Boolean deleteRegulation(List<Integer> ids);

    InvestorsResponse activeRegulation(String local, Integer id);

    List<InvestorsResponse> findAllIsActive(String local);

    List<InvestorsResponse> findAllNotIsActive(String local);

    List<InvestorsResponse> findAllTypeAndYearAndIsActive(String local, InvestorsSearchRequest investorsSearchRequest);

    List<InvestorsResponse> findAllYearAndIsActive(String local, Integer year);

    InvestorsResponse findAllYearAndIsActiveAndSort(String local, Integer year);

    PaginationInvestors findAllByTypeIsActive(String local, Integer type,Integer year, Integer page, Integer number);

    List<TypeInvestors> findAllTypeInvestors(String local);

    List<DetailTypeInvestors> findAllDetailTypeInvestors(String local, Integer id);

    List<PageInvestorsResponse> findAllByInvestorsId(String local, Integer typeInvestorsId,Integer year,Integer detailTypeId, Integer page, Integer number);
}
