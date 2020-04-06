package com.backend.bank.dto.response.news;

import com.backend.bank.dto.response.block.BlocksReponseDTO;
import lombok.Data;

@Data
public class NewsBlocksReponseDto {
    private Integer id;
    private Integer id_news;
    private BlocksReponseDTO blocks;
    private Integer position;
    private String title;
    private String content;
}
