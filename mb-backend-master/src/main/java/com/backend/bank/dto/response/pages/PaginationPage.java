package com.backend.bank.dto.response.pages;

import lombok.Data;

import java.util.List;
@Data
public class PaginationPage {
    private List<PagesResponseDto> pages;
    private Integer size;
}
