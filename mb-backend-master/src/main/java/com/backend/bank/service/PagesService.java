package com.backend.bank.service;


import com.backend.bank.dto.request.PagesRequestDto;
import com.backend.bank.dto.response.pages.PageBlockReponseDto;
import com.backend.bank.dto.response.pages.PagesResponseDto;
import com.backend.bank.dto.response.pages.PaginationPage;
import com.backend.bank.model.Pages;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface PagesService {
    List<PagesResponseDto> getPages(String locale);

    List<PagesResponseDto> findAllById(List<Integer> ids, String local);

    PaginationPage findAllByUser(String locale, Integer page, Integer number);

    PaginationPage findAllByIsActive(String locale, Integer page, Integer number, Boolean isActive);


    PaginationPage searchPages(String locale, Integer page, Integer number, Boolean isActive, String search);

    PagesResponseDto getPageById(int id , String local);
    PagesResponseDto getPageByName(String name,String local);
    PagesResponseDto addPages(PagesRequestDto pagesRequestDto, String locale);
    PagesResponseDto editPages(int id, PagesRequestDto pagesRequestDto, String locale);
    void deletePages(int id);
    PagesResponseDto changActivePage(int id, String locale);
    List<PagesResponseDto> sortPage(int parentId,String local);
    List<PagesResponseDto> updatePosition(int parentId, int pageId, int position,String locale);
    List<PageBlockReponseDto> updatePositions(int idPage,int idPageBlock,int position, String local);
    void deletePageBlock(Integer idPage, Integer idPageBlock);
    int getIdHomePage(String local);
    List<PagesResponseDto> getPageList(List<Integer> ids, String local);
    List<PagesResponseDto> getPageListByParent(Integer parent, String local);
}
