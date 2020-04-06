package com.backend.bank.dto.response.block;

import com.backend.bank.dto.response.block.BlocksReponseDTO;
import lombok.Data;

import java.util.List;

@Data
public class PaginationBlock {
    private List<BlocksReponseDTO> blocks;
    private Integer size;
}
