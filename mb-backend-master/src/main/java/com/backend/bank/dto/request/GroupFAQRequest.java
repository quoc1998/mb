package com.backend.bank.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GroupFAQRequest {
    @NotNull(message = "isActive not null")
    private Boolean isActive;

    @NotNull(message = "name not null")
    private String name;
}
