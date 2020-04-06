package com.backend.bank.dto.response.pages;

import com.backend.bank.dto.response.block.BlocksReponseDTO;
import lombok.Data;

@Data
public class PageBlockReponseDto {
    private Integer id;
    private Integer id_page;
    private BlocksReponseDTO blocks;
    private Integer position;
    private String title;
    private String name;
    private String content;
    private String contentHtml;
}
