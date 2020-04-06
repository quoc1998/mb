package com.backend.bank.dto.response.news;

import lombok.Data;

import java.util.List;

@Data
public class PaginationHotNews extends PaginationNews {
    private List<NewsReponseDto> hotNews;
    private String name;
    private String image;
}
