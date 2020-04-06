package com.backend.bank.controller;

import com.backend.bank.dto.request.BlockValueRequestDto;
import com.backend.bank.dto.response.block.BlockValuesReponseDTO;
import com.backend.bank.service.BlockValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/blocks")
public class BlockValueController {
    @Autowired
    BlockValueService blockValueService;
    @GetMapping("/{id}/blockValues")
    public List<BlockValuesReponseDTO> getAllBlockValue(@PathVariable("local") String local, @PathVariable("id") Integer blockId){
        return blockValueService.getAllBlockValues(local, blockId);
    }

    @GetMapping("/{id}/blockValues/{blockValueId}")
    public BlockValuesReponseDTO getBlockValue(@PathVariable("local") String local, @PathVariable("id") Integer id,
                                               @PathVariable("blockValueId") Integer blockValueId){
        return blockValueService.findById(local,id, blockValueId);
    }

    @PostMapping("/{id}/blockValues")

    public BlockValuesReponseDTO addBlockValue(@PathVariable("local") String local,
                                               @PathVariable("id") Integer blockId,
                                               @RequestBody BlockValueRequestDto blockValueRequestDto){


        return blockValueService.addBlockValue(local, blockId, blockValueRequestDto);
    }

    @PutMapping("/{id}/blockValues/{blockValueId}")
    public BlockValuesReponseDTO editBlockValue(@PathVariable("local") String local, @PathVariable("id") Integer id,
                                                @PathVariable("blockValueId") Integer blockValueId,
                                                @RequestBody BlockValueRequestDto blockValueRequestDto){
        return blockValueService.editBlockValue(local,id, blockValueId, blockValueRequestDto);
    }


    @DeleteMapping("/{id}/blockValues/{blockValueId}")
    public void deleteBlockValue(@PathVariable("local") String local, @PathVariable("id") Integer id,
                                 @PathVariable("blockValueId") Integer blockValueId){
        blockValueService.deleteBlockValue(local,id, blockValueId);
    }
}
