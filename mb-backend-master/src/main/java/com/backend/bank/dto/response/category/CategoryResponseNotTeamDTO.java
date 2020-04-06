package com.backend.bank.dto.response.category;

import lombok.Data;

@Data
public class CategoryResponseNotTeamDTO {
    private int id;
    private Integer parentId;
    private String slug;
    private int is_active;
    private int position;
    private  String base_image;
    private String name;
    private String description;
}
