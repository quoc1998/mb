package com.backend.bank.controller;

import com.backend.bank.dto.response.news.*;
import com.backend.bank.dto.response.pages.PagesResponseDto;
import com.backend.bank.dto.response.team.TeamReponseDTO;
import com.backend.bank.service.NewsService;
import com.backend.bank.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/{local}/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping
    public PaginationNews findAllByUser(@PathVariable("local") String local, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return newsService.findAllByUser(local, page, number);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/search")
    public PaginationNews findAllByTitle(@PathVariable("local") String local,
                                         @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                         @RequestParam(name = "search") String search,

                                         @RequestParam(name = "categoryId", required = false, defaultValue = "0") Integer categoryId
    ) {
        return newsService.findAllByTitle(local, page, number, search, categoryId);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/findallbycategory/{id}")
    public PaginationNews findAllByCategoryId(@PathVariable("local") String local, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                              @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                              @PathVariable("id") Integer id) {
        return newsService.findAllByCategoryId(local, page, number, id);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
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

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/{id}")
    public NewsReponseDto getNewsById(@PathVariable("id") int id, @PathVariable("local") String local) {
        return newsService.getNewsById(id, local);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/active")
    public PaginationNews findAllByActive(@PathVariable("local") String local,
                                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                          @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return newsService.findAllByIsActive(local, true, page, number);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/notactive")
    public PaginationNews findNewsNotActive(@PathVariable("local") String local, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                            @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return newsService.findAllByIsActive(local, false, page, number);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/url/{url}")
    public NewsReponseDto getNewsByUrl(@PathVariable("local") String local, @PathVariable("url") String url) {
        return newsService.getNewsByUrl(local, url);
    }

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
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

    @Secured({"ROLE_GET NEWS", "ROLE_XEM TIN TỨC"})
    @GetMapping("/findallnotpagination")
    public List<NewsReponseDto> findAllByIsActiveNotPagination(@PathVariable("local") String local) {
        return newsService.findAllByIsActiveNotPagination(local, true);
    }

    @Secured({"ROLE_CREATE NEWS", "ROLE_TẠO TIN TỨC"})
    @PostMapping()
    public NewsReponseDto addNews(@PathVariable("local") String local, @RequestBody NewDTO news) {
        return newsService.addNews(news, local);
    }

    @Secured({"ROLE_EDIT NEWS", "ROLE_CHỈNH SỬA TIN TỨC"})
    @PutMapping("/{id}")
    public NewsReponseDto editNew(@PathVariable("id") int id, @PathVariable("local") String local, @RequestBody NewDTO newsRequestDto) {
        return newsService.editNews(id, newsRequestDto, local);
    }

    @Secured({"ROLE_DELETE NEWS", "ROLE_XÓA TIN TỨC"})
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable("id") int id, @PathVariable("local") String local) {
        newsService.deleteNew(id, local);
    }

    @Secured({"ROLE_ACCEPT NEWS", "ROLE_DUYỆT TIN TỨC"})
    @PutMapping("/accept/{id}")
    public NewsReponseDto acceptActive(@PathVariable("id") int id, @PathVariable("local") String local) {
        return newsService.acceptNews(id, local);
    }

    @Secured({"ROLE_UPDATE POSITION NEWSBLOCK", "ROLE_CHỈNH SỬA VỊ TRÍ NEWSBLOCK"})
    @PutMapping("/update_position_newBlock/{idNew}/{idNewBlock}/{position}")
    public List<NewsBlocksReponseDto> updatePosition(@PathVariable("local") String local, @PathVariable("idNew") int idNew
            , @PathVariable("idNewBlock") int idNewBlock, @PathVariable("position") int position) {
        return newsService.updatePositions(idNew, idNewBlock, position, local);
    }

    @Secured({"ROLE_DELETE NEWS", "ROLE_XÓA TIN TỨC"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListNews(@RequestBody List<Integer> ids) {
        return newsService.deleteIds(ids);
    }


    @GetMapping("/importfileexcel")
    public void deleteListNews(@PathVariable("local") String local) {
        newsService.readExcelNews(local);
    }

}

