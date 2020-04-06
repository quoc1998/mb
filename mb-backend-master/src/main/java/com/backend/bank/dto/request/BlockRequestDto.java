package com.backend.bank.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BlockRequestDto {

    private Boolean active;

    private String name;

    private Integer tagId;

    private String image;

    private String html;

    private List<BlockValueRequestDto> blockValues;
}
