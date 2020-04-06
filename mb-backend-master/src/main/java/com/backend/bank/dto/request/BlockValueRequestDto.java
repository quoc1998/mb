package com.backend.bank.dto.request;

import lombok.Data;

@Data
public class BlockValueRequestDto {

    private Integer id;

    private Integer position;

    private String key;

    private String title;

    private Integer type_id;
}
