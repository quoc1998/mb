package com.backend.bank.dto.response.block;

import com.backend.bank.model.BlockValues;
import lombok.Data;

import java.util.Date;

@Data
public class BlockValuesReponseDTO {
    private Integer id;

    private int block_id;

    private String key;

    private Integer position;

    private String title;

    private Integer type_id;

}
