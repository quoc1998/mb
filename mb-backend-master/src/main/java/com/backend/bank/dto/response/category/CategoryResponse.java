package com.backend.bank.dto.response.category;

import com.backend.bank.dto.response.team.TeamDTO;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private int id;
    private Integer parentId;
    private String slug;
    private int is_active;
    private int position;
    private  String base_image;
    private String name;
    private String description;
    private List<TeamDTO> teams;
}
