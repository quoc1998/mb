package com.backend.bank.dto.response;

import com.backend.bank.dto.response.user.RoleDTO;
import lombok.Data;

import java.util.List;

@Data
public class PaginationRole {
    private List<RoleDTO> roles;
    private Integer size;
}
