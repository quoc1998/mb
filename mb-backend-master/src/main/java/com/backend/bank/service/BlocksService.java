package com.backend.bank.service;

import com.backend.bank.dto.response.block.BlocksReponseDTO;
import com.backend.bank.dto.request.BlockRequestDto;
import com.backend.bank.dto.response.block.PaginationBlock;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlocksService {
    List<BlocksReponseDTO> getBlocks(String local);
    PaginationBlock findAllPagin(String locale, Integer page, Integer number);
    PaginationBlock getBlocksSearch(String locale, Integer page, Integer number, String search);
    BlocksReponseDTO findById(String local, Integer id );
    BlocksReponseDTO addBlock(String local,BlockRequestDto blocksDTO );
    BlocksReponseDTO editBlock(int id , String local,BlockRequestDto blocksDTO);
    void deleteBlock(int id, String local);
}
