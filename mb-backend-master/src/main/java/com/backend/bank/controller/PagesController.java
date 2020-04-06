package com.backend.bank.controller;

import com.backend.bank.dto.request.PagesRequestDto;
import com.backend.bank.dto.response.pages.PageBlockReponseDto;
import com.backend.bank.dto.response.pages.PagesResponseDto;
import com.backend.bank.dto.response.pages.PaginationPage;
import com.backend.bank.service.PagesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("{locale}/api/pages")
public class PagesController {

    @Autowired
    private PagesService pagesService;


    @Secured({"ROLE_GET PAGE", "ROLE_XEM PAGE"})
    @GetMapping
    public List<PagesResponseDto> getPages(@PathVariable("locale") String locale) {
        return pagesService.getPages(locale);
    }


    @Secured({"ROLE_GET PAGE", "ROLE_XEM PAGE"})
    @GetMapping("/pagination")
    public PaginationPage findAllByUser(@PathVariable("locale") String locale, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                        @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return pagesService.findAllByUser(locale, page, number);
    }

    @Secured({"ROLE_GET PAGE", "ROLE_XEM PAGE"})
    @GetMapping("/findallnotactive")
    public PaginationPage findAllByIsActive(@PathVariable("locale") String locale, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                            @RequestParam(name = "number", required = false, defaultValue = "10") Integer number) {
        return pagesService.findAllByIsActive(locale, page, number, false);
    }

    @Secured({"ROLE_GET PAGE", "ROLE_XEM PAGE"})
    @GetMapping("/{id}")
    public PagesResponseDto getPageById(@PathVariable("id") int id, @PathVariable("locale") String locale) {
        return pagesService.getPageById(id, locale);
    }


    @Secured({"ROLE_GET PAGE", "ROLE_XEM PAGE"})
    @GetMapping("/parent/{parentId}")
    public List<PagesResponseDto> getPageByParentId(@PathVariable("parentId") Integer parentId, @PathVariable("locale") String locale) {
        return pagesService.getPageListByParent(parentId, locale);
    }

    @PostMapping("name")
    public PagesResponseDto getPageByName(@RequestParam("name") String name, @PathVariable("locale") String locale) {
        return pagesService.getPageByName(name, locale);
    }

    @Secured({"ROLE_CREATE PAGE", "ROLE_TẠO PAGE"})
    @PostMapping
    public PagesResponseDto addPages(@Valid @RequestBody PagesRequestDto pagesRequestDto, @PathVariable("locale") String locale) {
        return pagesService.addPages(pagesRequestDto, locale);
    }

    @Secured({"ROLE_EDIT PAGE", "ROLE_CHỈNH SỬA PAGE"})
    @PutMapping("/{id}")
    public ResponseEntity<PagesResponseDto> editPages(@PathVariable("id") int id, @PathVariable("locale") String locale,
                                                      @Valid @RequestBody PagesRequestDto pagesRequestDto) {
        return new ResponseEntity<>(pagesService.editPages(id, pagesRequestDto, locale), HttpStatus.OK);
    }

    @Secured({"ROLE_DELETE PAGE", "ROLE_XÓA PAGE"})
    @DeleteMapping("/{id}")
    public void deletePages(@PathVariable("locale") String local, @PathVariable("id") int id) {
        pagesService.deletePages(id);
    }

    @Secured({"ROLE_ACCEPT PAGES", "ROLE_DUYỆT TRANG"})
    @PutMapping("/change_active/{id}")
    public PagesResponseDto changeActivePage(@PathVariable("locale") String local, @PathVariable("id") int id) {
        return pagesService.changActivePage(id, local);
    }

    @GetMapping("/sort/{id}")
    public List<PagesResponseDto> sortPages(@PathVariable("id") int id, @PathVariable("locale") String locale) {
        return pagesService.sortPage(id, locale);
    }

    @Secured({"ROLE_UPDATE POSITION PAGE", "ROLE_CHỈNH SỬA VỊ TRÍ PAGE"})
    @PutMapping("/update_position/{idParent}/{idPage}")
    public List<PagesResponseDto> updatePosition(@PathVariable("idParent") int idParent, @PathVariable("idPage") int idPage,
                                                 @RequestParam(value = "position", required = true) int position, @PathVariable("locale") String locale) {
        return pagesService.updatePosition(idParent, idPage, position, locale);
    }

    @DeleteMapping("{id}/pageBlock/{idPageBlock}")
    public void deletePageBlock(@PathVariable("id") int id, @PathVariable("idPageBlock") int idPageBlock) {
        pagesService.deletePageBlock(id, idPageBlock);
    }

    @Secured({"ROLE_UPDATE POSITION PAGE", "ROLE_CHỈNH SỬA VỊ TRÍ PAGE"})
    @PutMapping("update_position_page_block/{idPage}/{idPageBlock}/{position}")
    public List<PageBlockReponseDto> updatePositions(@PathVariable("idPage") int idPage,
                                                     @PathVariable("idPageBlock") int idPageBlock,
                                                     @PathVariable("position") int position,
                                                     @PathVariable("locale") String locale) {

        return pagesService.updatePositions(idPage, idPageBlock, position, locale);
    }

    @GetMapping("/homepage")
    public int getIdHomePage(@PathVariable("locale") String locale) {
        return pagesService.getIdHomePage(locale);
    }

    @PostMapping("ids")
    public List<PagesResponseDto> getListPages(@RequestBody List<Integer> ids, @PathVariable("locale") String locale) {
        return pagesService.getPageList(ids, locale);
    }

    @Secured({"ROLE_GET PAGE", "ROLE_XEM PAGE"})
    @GetMapping("/search")
    public PaginationPage searchPages(@PathVariable("locale") String locale, @RequestParam(name = "page", required = false,
                                        defaultValue = "0") Integer page,
                                        @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                        @RequestParam(name = "search", required = false, defaultValue = "") String search) {
        return pagesService.searchPages(locale, page, number,true, search);
    }
}
