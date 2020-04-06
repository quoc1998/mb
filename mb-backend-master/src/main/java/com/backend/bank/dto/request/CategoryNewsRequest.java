package com.backend.bank.dto.request;

import lombok.Data;

@Data
public class CategoryNewsRequest {
    private int id;
    private String name;
    private String slug;

    public CategoryNewsRequest() {
    }

}
