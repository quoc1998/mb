package com.backend.bank.controller.fontend;

import com.backend.bank.dto.request.InterestRateRequestDto;
import com.backend.bank.dto.response.toolbar.InterestRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationInterestRate;
import com.backend.bank.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/fe/interest_rate")
public class InterestRateFrontendController {
    @Autowired
    private InterestRateService interestRateService;


    @GetMapping
    public List<InterestRateReponseDto> getAll(@PathVariable("locale") String locale) {
        return interestRateService.getAll(locale);
    }


    @GetMapping("/pagination")
    public PaginationInterestRate findAllPage(@PathVariable("locale") String locale,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return interestRateService.findAllPagin(locale, page, number);
    }



    @GetMapping("/{id}")
    public InterestRateReponseDto getInterestRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return interestRateService.getInterestRate(locale, id);
    }

}
