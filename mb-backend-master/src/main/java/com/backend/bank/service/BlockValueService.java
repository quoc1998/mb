package com.backend.bank.service;

import com.backend.bank.dto.request.BlockValueRequestDto;
import com.backend.bank.dto.response.block.BlockValuesReponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlockValueService {
    List<BlockValuesReponseDTO> getAllBlockValues(String local, Integer blockIsd);
    BlockValuesReponseDTO addBlockValue(String local,Integer blockId, BlockValueRequestDto blockValueDTO);
    BlockValuesReponseDTO findById(String local, Integer idBlock, Integer idBlockValue);

    BlockValuesReponseDTO editBlockValue(String local, Integer idBlock,Integer idBlockValue, BlockValueRequestDto blockValueDTO);
    void deleteBlockValue(String local, Integer idBlock, Integer idBlockValue);

}
