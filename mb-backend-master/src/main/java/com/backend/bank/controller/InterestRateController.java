package com.backend.bank.controller;

import com.backend.bank.dto.request.InterestRateRequestDto;
import com.backend.bank.dto.response.toolbar.InterestRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationInterestRate;
import com.backend.bank.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/interest_rate")
public class InterestRateController {
    @Autowired
    private InterestRateService interestRateService;

    @Secured({"ROLE_GET INTEREST_RATE", "ROLE_XEM LÃI SUẤT"})
    @GetMapping
    public List<InterestRateReponseDto> getAll(@PathVariable("locale") String locale) {
        return interestRateService.getAll(locale);
    }

    @Secured({"ROLE_GET INTEREST_RATE", "ROLE_XEM LÃI SUẤT"})
    @GetMapping("/pagination")
    public PaginationInterestRate findAllPage(@PathVariable("locale") String locale,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return interestRateService.findAllPagin(locale, page, number);
    }

    @Secured({"ROLE_CREATE INTEREST_RATE", "ROLE_TẠO LÃI SUẤT"})
    @PostMapping
    public InterestRateReponseDto addInterestRate(@PathVariable("locale") String locale, @RequestBody InterestRateRequestDto interestRateRequestDto) {
        return interestRateService.addInterestRate(locale, interestRateRequestDto);
    }

    @Secured({"ROLE_GET INTEREST_RATE", "ROLE_XEM LÃI SUẤT"})
    @GetMapping("/{id}")
    public InterestRateReponseDto getInterestRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return interestRateService.getInterestRate(locale, id);
    }

    @Secured({"ROLE_EDIT INTEREST_RATE", "ROLE_SỬA LÃI SUẤT"})
    @PutMapping("/{id}")
    public InterestRateReponseDto editInterestRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id, @RequestBody InterestRateRequestDto interestRateRequestDto) {
        return interestRateService.editInterestRate(locale, id, interestRateRequestDto);
    }

    @Secured({"ROLE_DELETE INTEREST_RATE", "ROLE_XÓA LÃI SUẤT"})
    @DeleteMapping("/{id}")
    public void deleteInterestRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        interestRateService.deleteInterestRate(locale, id);
    }

    @Secured({"ROLE_DELETE INTEREST_RATE", "ROLE_XÓA LÃI SUẤT"})
    @DeleteMapping
    public void deleteInterstRates(@PathVariable("locale") String locale, @RequestBody List<Integer> listId) {
        for (int id : listId) {
            interestRateService.deleteInterestRate(locale, id);
        }
    }
}
