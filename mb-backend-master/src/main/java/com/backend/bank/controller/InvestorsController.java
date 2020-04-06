package com.backend.bank.controller;

import com.backend.bank.dto.request.InvestorsRequest;
import com.backend.bank.dto.request.InvestorsSearchRequest;
import com.backend.bank.dto.response.investors.InvestorsResponse;
import com.backend.bank.dto.response.investors.PageInvestorsResponse;
import com.backend.bank.dto.response.investors.PaginationInvestors;
import com.backend.bank.model.DetailTypeInvestors;
import com.backend.bank.model.TypeInvestors;
import com.backend.bank.service.InvestorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{local}/api/regulation")
public class InvestorsController {
    @Autowired
    public InvestorsService investorsService;

    @GetMapping
    public List<InvestorsResponse> findAll(@PathVariable("local") String local) {
        return investorsService.findAll(local);
    }

    @GetMapping("/{id}")
    public InvestorsResponse findById(@PathVariable("local") String locale, @PathVariable("id") Integer id) {
        return investorsService.findById(locale, id);
    }

    @PostMapping()
    public Boolean addRegulation(@PathVariable("local") String locale, @RequestBody InvestorsRequest investorsRequest) {
        return investorsService.addRegulation(locale, investorsRequest);
    }

    @PutMapping()
    public Boolean editRegulation(@PathVariable("local") String locale, Integer id, @RequestBody InvestorsRequest investorsRequest) {
        return investorsService.editRegulation(locale,id, investorsRequest);
    }

    @DeleteMapping()
    public Boolean deleteRegulation(@PathVariable("local") String locale, @RequestBody List<Integer> ids) {
        return investorsService.deleteRegulation(ids);
    }

    @GetMapping("/allisactive")
    public List<InvestorsResponse> findAllIsActive(@PathVariable("local") String locale) {
        return investorsService.findAllIsActive(locale);
    }

    ;

    @GetMapping("/allnotisactive")
    public List<InvestorsResponse> findAllNotIsActive(@PathVariable("local") String locale) {
        return investorsService.findAllNotIsActive(locale);
    }

    ;

    @PostMapping("/search")
    public List<InvestorsResponse> findAllTypeAndYearAndIsActive(@PathVariable("local") String locale, @RequestBody InvestorsSearchRequest investorsSearchRequest) {
        return investorsService.findAllTypeAndYearAndIsActive(locale, investorsSearchRequest);
    }

    @GetMapping("/year/{year}")
    public List<InvestorsResponse> findAllYearAndIsActive(@PathVariable("local") String locale, @PathVariable("year") Integer year) {
        return investorsService.findAllYearAndIsActive(locale,year);
    }

    @GetMapping("/url/{year}")
    public InvestorsResponse findAllYearAndIsActiveAndSort(@PathVariable("local") String locale, @PathVariable("year") Integer year) {
        return investorsService.findAllYearAndIsActiveAndSort(locale,year);
    }

    @GetMapping("/pagination/{type}/{year}")
    public PaginationInvestors findAllByUser(@PathVariable("local") String locale,
                                             @PathVariable("type") Integer type, @PathVariable("year") Integer year,
                                             @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                             @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return investorsService.findAllByTypeIsActive(locale,type, year, page, number);
    }

    @PutMapping("/active/{id}")
    public InvestorsResponse activeRegulation(@PathVariable("local") String locale, @PathVariable("id") Integer id) {
        return investorsService.activeRegulation(locale,id);
    }

    @GetMapping("/type")
    public List<TypeInvestors> findAllTypeInvestors(@PathVariable("local") String locale) {
        return investorsService.findAllTypeInvestors(locale);
    }

    @GetMapping("/type/{id}")
    public List<DetailTypeInvestors> findAllDetailTypeInvestors(@PathVariable("local") String locale, @PathVariable("id") Integer id) {
        return investorsService.findAllDetailTypeInvestors(locale,id);
    }

    @GetMapping("/investors/{id}")
    public List<PageInvestorsResponse> findAllByInvestorsId(@PathVariable("local") String locale, @PathVariable("id") Integer id,
                                                            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                            @RequestParam(name = "detailTypeId", required = false, defaultValue = "0") Integer detailTypeId,
                                                            @RequestParam(name = "year", required = false, defaultValue = "0") Integer year,
                                                            @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return investorsService.findAllByInvestorsId(locale,id, detailTypeId, year, page, number);
    }
}
