package com.backend.bank.dto.response.team;

import lombok.Data;

import java.util.List;
@Data
public class PaginationTeam {
    private List<TeamReponseDTO> teams;
    private Integer size;
}
