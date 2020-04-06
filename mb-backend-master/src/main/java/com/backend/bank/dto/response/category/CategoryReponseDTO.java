package com.backend.bank.dto.response.category;

import com.backend.bank.dto.response.news.NewsReponseDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryReponseDTO {
    private int id;
    private String name;
    private int is_active;
    private List<NewsReponseDto> news;
}
