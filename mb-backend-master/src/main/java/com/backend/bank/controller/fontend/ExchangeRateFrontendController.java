package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.toolbar.ExchangeRateReponseDto;
import com.backend.bank.dto.response.toolbar.PaginationExchangeRate;
import com.backend.bank.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/fe/exchange_rate")
public class ExchangeRateFrontendController {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping
    public List<ExchangeRateReponseDto> getAll(@PathVariable("locale") String locale) {
        return exchangeRateService.getAll(locale);
    }

    @GetMapping("/pagination")
    public PaginationExchangeRate findAllPage(@PathVariable("locale") String locale,
                                              @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return exchangeRateService.findAllPagin(locale, page, number);
    }

    @GetMapping("/search")
    public PaginationExchangeRate findRangeDate(@PathVariable("locale") String locale,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                                @RequestParam(name = "dateStart") String dateStart,
                                                @RequestParam(name = "dateEnd") String dateEnd) {
        return exchangeRateService.findRangeDate(locale, page, number, dateStart, dateEnd);
    }


    @GetMapping("/{id}")
    public ExchangeRateReponseDto getExchangeRate(@PathVariable("locale") String locale, @PathVariable("id") Integer id) {
        return exchangeRateService.getExchangeRate(locale, id);
    }


    @GetMapping("/date/")
    public ExchangeRateReponseDto getExchangeRate(@PathVariable("locale") String locale,
                                                  @RequestParam(name = "date", required = false,
                                                          defaultValue = "0000-00-00") String date) {
        return exchangeRateService.getExchangeRate(date);
    }


    @GetMapping("/news/")
    public ExchangeRateReponseDto getExchangeRateNew(@PathVariable("locale") String locale) {
        return exchangeRateService.getExchangeRateNew();
    }

}
