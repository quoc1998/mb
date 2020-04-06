package com.backend.bank.dto.request;

import com.backend.bank.dto.response.team.TeamDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CategoryRequest {
    private int id;
    @NotNull(message = "parentId not null")
    private Integer parentId;

    @NotNull(message = "slug not null")
    private String slug;

    @NotNull(message = "is_active not null")
    private int is_active;

    @NotNull(message = "position not null")
    private int position;

    @NotNull(message = "base_image not null")
    private  String base_image;

    @NotNull(message = "name not null")
    private String name;

    @NotNull(message = "description not null")
    private String description;

    @NotNull(message = "team not null")
    private List<TeamDTO> teams;
}
