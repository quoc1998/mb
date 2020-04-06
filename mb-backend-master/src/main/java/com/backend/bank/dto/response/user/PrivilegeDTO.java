package com.backend.bank.dto.response.user;

import lombok.Data;

import java.util.List;

@Data
public class PrivilegeDTO {
    private String groupRole;
    private List<PrivilegeResponse> privileges;
}
