package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.pages.PagesResponseDto;
import com.backend.bank.service.PagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{locale}/api/fe/pages")
public class PagesFrontendController {

    @Autowired
    private PagesService pagesService;

    @GetMapping
    public List<PagesResponseDto> getPages(@PathVariable("locale") String locale) {
        return pagesService.getPages(locale);
    }

    @PostMapping("name")
    public PagesResponseDto getPageByName(@RequestParam("name") String name, @PathVariable("locale") String locale) {
        return pagesService.getPageByName(name, locale);
    }


    @GetMapping("/homepage")
    public int getIdHomePage(@PathVariable("locale") String locale) {
        return pagesService.getIdHomePage(locale);
    }

    @PostMapping("ids")
    public List<PagesResponseDto> getListPages(@RequestBody List<Integer> ids, @PathVariable("locale") String locale) {
        return pagesService.getPageList(ids, locale);
    }

}
