package com.backend.bank.dto.response.team;

import com.backend.bank.dto.response.category.CategoryReponseDTO;
import com.backend.bank.dto.response.news.NewsReponseDto;
import com.backend.bank.dto.response.pages.PagesResponseDto;
import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
public class TeamReponseDTO {
    private int id;
    private String name;
    private String local;
    private int idTeam;
    private List<PagesResponseDto> pages;
    private List<CategoryReponseDTO> category;
    private HashSet<NewsReponseDto> news;
}
