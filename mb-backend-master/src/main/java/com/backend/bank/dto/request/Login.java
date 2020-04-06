package com.backend.bank.dto.request;

import lombok.Data;

@Data
public class Login {
    private String username;
    private String password;
}
