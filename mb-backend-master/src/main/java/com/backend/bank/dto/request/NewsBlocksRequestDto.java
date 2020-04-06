package com.backend.bank.dto.request;

import lombok.Data;

@Data
public class NewsBlocksRequestDto {
    private Integer id;
    private Integer id_block;
    private Integer position;
    private Integer id_news;
    private String title;
    private String content;
}
