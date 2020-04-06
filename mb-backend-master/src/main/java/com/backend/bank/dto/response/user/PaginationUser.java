package com.backend.bank.dto.response.user;

import lombok.Data;

import java.util.List;

@Data
public class PaginationUser {
    private List<UserResponseDto> users;
    private Integer size;
}
