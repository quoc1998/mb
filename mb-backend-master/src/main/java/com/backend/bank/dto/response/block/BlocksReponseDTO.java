package com.backend.bank.dto.response.block;

import lombok.Data;

import java.util.List;

@Data
public class BlocksReponseDTO {
    private Integer id;

    private Boolean active;

    private String name;

    private String image;

    private Integer tagId;

    private String html;

    private List<BlockValuesReponseDTO> blockValues;

}
