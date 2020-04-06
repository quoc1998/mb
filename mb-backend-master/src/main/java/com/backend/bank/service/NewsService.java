package com.backend.bank.service;


import com.backend.bank.dto.response.news.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NewsService {
    List<NewsReponseDto> findAllById(String local, List<Integer> ids);

    PaginationNews findAllByUser(String local, Integer page, Integer number);

    PaginationNews findAllByIsActive(String local, Boolean isActive, Integer page, Integer number);

    List<NewsReponseDto> findAllByIsActiveNotPagination(String local, Boolean isActive);

    PaginationNews findAllPagination(String locale, Integer page, Integer number);

    List<NewsReponseDto> getNewsByCategoryIsActive(int idCategory, String local);

    NewsReponseDto getNewsById(int id, String local);

    NewsReponseDto addNews(NewDTO newDTO, String local);

    NewsReponseDto editNews(int id, NewDTO newDTO, String local);

    void deleteNew(int id, String local);

    PaginationNews findAllByTitle(String local, Integer page, Integer number, String search, Integer categoryId);

    PaginationNews findAllByCategoryId(String local, Integer page, Integer number, Integer categoryId);

    PaginationHotNews findAllByCategorySlug(String local, Integer page, Integer number, String categorySlug);

    PaginationNews findAllByCategoryIdAndYear(String local, Integer page, Integer number, Integer categoryId, Integer year);

    NewsReponseDto acceptNews(int id, String local);

    List<NewsBlocksReponseDto> updatePositions(int idNew, int idNewBlock, int position, String local);

    NewsReponseDto getNewsByUrl(String local, String name);

    Boolean deleteIds(List<Integer> ids);

    void readExcelNews(String local);
}
