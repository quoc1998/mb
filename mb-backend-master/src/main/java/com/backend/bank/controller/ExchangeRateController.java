package com.backend.bank.controller;

import com.backend.bank.dto.request.ExchangeRateRequestDto;
import com.backend.bank.dto.response.toolbar.ExchangeRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationExchangeRate;
import com.backend.bank.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("{locale}/api/exchange_rate")
public class ExchangeRateController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @Secured({"ROLE_GET EXCHANGE_RATE", "ROLE_XEM TỈ GIÁ"})
    @GetMapping
    public List<ExchangeRateReponseDto> getAll(@PathVariable("locale") String locale) {
        return exchangeRateService.getAll(locale);
    }

    @Secured({"ROLE_GET EXCHANGE_RATE", "ROLE_XEM TỈ GIÁ"})
    @GetMapping("/pagination")
    public PaginationExchangeRate findAllPage(@PathVariable("locale") String locale,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return exchangeRateService.findAllPagin(locale, page, number);
    }

    @Secured({"ROLE_GET EXCHANGE_RATE", "ROLE_XEM TỈ GIÁ"})
    @GetMapping("/search")
    public PaginationExchangeRate findRangeDate(@PathVariable("locale") String locale,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                                @RequestParam(name = "dateStart") String dateStart,
                                                @RequestParam(name = "dateEnd") String dateEnd) {
        return exchangeRateService.findRangeDate(locale, page, number, dateStart, dateEnd);
    }


    @Secured({"ROLE_CREATE EXCHANGE_RATE", "ROLE_TẠO TỈ GIÁ"})
    @PostMapping
    public ExchangeRateReponseDto addExchangeRate(@PathVariable("locale") String locale, @RequestBody ExchangeRateRequestDto exchangeRateRequestDto) {
        return exchangeRateService.addExchangeRate(locale, exchangeRateRequestDto);
    }

    @Secured({"ROLE_GET EXCHANGE_RATE", "ROLE_XEM TỈ GIÁ"})
    @GetMapping("/{id}")
    public ExchangeRateReponseDto getExchangeRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return exchangeRateService.getExchangeRate(locale, id);
    }

    @Secured({"ROLE_EDIT EXCHANGE_RATE", "ROLE_SỬA TỈ GIÁ"})
    @PutMapping("/{id}")
    public ExchangeRateReponseDto editExchangeRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id, @RequestBody ExchangeRateRequestDto exchangeRateRequestDto) {
        return exchangeRateService.editExchangeRate(locale, id, exchangeRateRequestDto);
    }

    @Secured({"ROLE_DELETE EXCHANGE_RATE", "ROLE_XÓA TỈ GIÁ"})
    @DeleteMapping("/{id}")
    public void deleteExchangeRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        exchangeRateService.deleteExchangeRate(locale, id);
    }

    @Secured({"ROLE_DELETE EXCHANGE_RATE", "ROLE_XÓA TỈ GIÁ"})
    @DeleteMapping
    public void deleteExchangeRates(@PathVariable("locale") String locale, @RequestBody List<Integer> listId) {
        for (int id : listId) {
            exchangeRateService.deleteExchangeRate(locale, id);
        }
    }

    @Secured({"ROLE_GET EXCHANGE_RATE", "ROLE_XEM TỈ GIÁ"})
    @GetMapping("/date/")
    public ExchangeRateReponseDto getExchangeRate(@PathVariable("locale") String locale,
                                                  @RequestParam(name = "date", required = false,
                                                          defaultValue = "0000-00-00") String date) {
        return exchangeRateService.getExchangeRate(date);
    }

    @Secured({"ROLE_GET EXCHANGE_RATE", "ROLE_XEM TỈ GIÁ"})
    @GetMapping("/news/")
    public ExchangeRateReponseDto getExchangeRateNew(@PathVariable("locale") String locale) {
        return exchangeRateService.getExchangeRateNew();
    }


    @GetMapping("/importExchangeRate/")
    public void importExchangeRate(@PathVariable("locale") String locale) {
        exchangeRateService.importExchangeRate(locale);
    }

}
