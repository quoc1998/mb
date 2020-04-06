package com.backend.bank.dto.response;

import lombok.Data;

import java.util.Date;

@Data
public class GroupFAQResponse {
    private Integer id;
    private Boolean isActive;
    private String name;
    private Date createAt;
}
