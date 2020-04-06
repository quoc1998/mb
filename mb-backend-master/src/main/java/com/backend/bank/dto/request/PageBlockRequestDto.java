package com.backend.bank.dto.request;

import lombok.Data;

@Data
public class PageBlockRequestDto {
    private Integer id;
    private Integer id_page;
    private Integer id_block;
    private Integer position;
    private String title;
    private String content;
    private String name;
    private String contentHtml;
}
