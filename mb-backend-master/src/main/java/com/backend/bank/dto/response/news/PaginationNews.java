package com.backend.bank.dto.response.news;

import lombok.Data;

import java.util.List;

@Data
public class PaginationNews {
    private List<NewsReponseDto> news;
    private Integer size;
}
