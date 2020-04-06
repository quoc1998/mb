package com.backend.bank.dto.response;

import com.backend.bank.dto.response.block.BlocksReponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class TagReponseDto {
    private Integer id;
    private String name;
    private List<BlocksReponseDTO> Blocks;
}
