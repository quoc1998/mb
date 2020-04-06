package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.news.NewsReponseDto;
import com.backend.bank.dto.response.news.PaginationHotNews;
import com.backend.bank.dto.response.news.PaginationNews;
import com.backend.bank.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/fe/news")
public class NewsFrontendController {

    @Autowired
    private NewsService newsService;


    @GetMapping
    public PaginationNews findAll(@PathVariable("local") String local, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return newsService.findAllPagination(local, page, number);
    }


    @GetMapping("/findallbycategory/{id}")
    public PaginationNews findAllByCategoryId(@PathVariable("local") String local, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                              @PathVariable("id") Integer id) {
        return newsService.findAllByCategoryId(local, page, number, id);
    }


    @GetMapping("/findallbycategoryandyear/{id}")
    public PaginationNews findAllByCategoryIdAndYear(@PathVariable("local") String local,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                     @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                                     @RequestParam(name = "year", required = false, defaultValue = "2020") Integer year,
                                                     @PathVariable("id") Integer id) {
        return newsService.findAllByCategoryIdAndYear(local, page, number, id, year);
    }

    @PostMapping("ids")
    public List<NewsReponseDto> getListPages(@RequestBody List<Integer> ids, @PathVariable("local") String local) {
        return newsService.findAllById(local, ids);
    }


    @GetMapping("/{id}")
    public NewsReponseDto getNewsById(@PathVariable("id") int id, @PathVariable("local") String local) {
        return newsService.getNewsById(id, local);
    }


    @GetMapping("/active")
    public PaginationNews findAllByActive(@PathVariable("local") String local,
                                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                          @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return newsService.findAllByIsActive(local, true, page, number);
    }


    @GetMapping("/url/{url}")
    public NewsReponseDto getNewsByUrl(@PathVariable("local") String local, @PathVariable("url") String url) {
        return newsService.getNewsByUrl(local, url);
    }

    @GetMapping("/category_is_active/{idCategory}")
    public List<NewsReponseDto> getNewsByCategory(@PathVariable("idCategory") int idCategory, @PathVariable("local") String local) {
        return newsService.getNewsByCategoryIsActive(idCategory, local);
    }


    @GetMapping("/findallbyslugcategory/{url}")
    public PaginationHotNews findAllByCategorySlug(@PathVariable("local") String local, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                                   @PathVariable("url") String url) {
        return newsService.findAllByCategorySlug(local, page, number, url);
    }

    @GetMapping("/findallnotpagination")
    public List<NewsReponseDto> findAllByIsActiveNotPagination(@PathVariable("local") String local) {
        return newsService.findAllByIsActiveNotPagination(local, true);
    }

}

