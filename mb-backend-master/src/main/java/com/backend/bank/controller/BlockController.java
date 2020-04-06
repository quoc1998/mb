package com.backend.bank.controller;

import com.backend.bank.dto.response.block.BlocksReponseDTO;
import com.backend.bank.dto.request.BlockRequestDto;
import com.backend.bank.dto.response.block.PaginationBlock;
import com.backend.bank.service.BlockValueService;
import com.backend.bank.service.BlocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/blocks")
public class BlockController {
    @Autowired
    private BlocksService blocksService;

    @Autowired
    BlockValueService blockValueService;

    @Secured({"ROLE_GET BLOCK","ROLE_XEM BLOCK"})
    @GetMapping
    public List<BlocksReponseDTO> findByAll(@PathVariable("local") String local){
        return blocksService.getBlocks(local);
    }
    @Secured({"ROLE_GET BLOCK","ROLE_XEM BLOCK"})
    @GetMapping("/pagination")
    public PaginationBlock findAllPage(@PathVariable("local") String local,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(name = "number", required = false, defaultValue = "10") Integer number){

        return blocksService.findAllPagin(local, page, number);
    }

    @Secured({"ROLE_GET BLOCK","ROLE_XEM BLOCK"})
    @GetMapping("/search")
    public PaginationBlock getSearch(@PathVariable("local") String local,
                                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(name = "number", required = false, defaultValue = "10") Integer number,
                                     @RequestParam("search") String search){

        return blocksService.getBlocksSearch(local, page, number, search);
    }

    @Secured({"ROLE_GET BLOCK","ROLE_XEM BLOCK"})
    @GetMapping("{id}")
    public BlocksReponseDTO findById(@PathVariable("local") String local, @PathVariable("id") Integer id){
        return blocksService.findById(local, id);
    }

    @Secured({"ROLE_ADD BLOCK","ROLE_THÊM BLOCK"})
    @PostMapping
    public BlocksReponseDTO addBlock(@PathVariable("local") String local,@RequestBody BlockRequestDto blocksDTO ){
        return blocksService.addBlock(local, blocksDTO);
    }

    @Secured({"ROLE_EDIT BLOCK","ROLE_SỬA BLOCK"})
    @PutMapping("/{id}")
    public BlocksReponseDTO editBlock(@PathVariable("local") String local, @PathVariable("id") int id, @RequestBody BlockRequestDto blocksDTO){
        return blocksService.editBlock(id, local, blocksDTO);
    }

    @Secured({"ROLE_DELETE BLOCK","ROLE_XÓA BLOCK"})
    @DeleteMapping("/{id}")
    public void deleteBlock(@PathVariable("local") String local, @PathVariable("id") int id){
        blocksService.deleteBlock(id, local);
    }

}
