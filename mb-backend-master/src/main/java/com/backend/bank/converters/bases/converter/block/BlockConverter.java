package com.backend.bank.converters.bases.converter.block;

import com.backend.bank.dto.request.BlockValueRequestDto;
import com.backend.bank.dto.response.block.BlockValuesReponseDTO;
import com.backend.bank.dto.response.block.BlocksReponseDTO;
import com.backend.bank.dto.request.BlockRequestDto;
import com.backend.bank.model.*;
import com.backend.bank.repository.BlockTranslationsRepository;
import com.backend.bank.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BlockConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    BlockTranslationsRepository blockTranslationsRepository;

    @Autowired
    private BlockValueConverter blockValueConverter;

    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();

    public BlocksReponseDTO converterBlockReponse(String local, Blocks blocks) {
        BlocksReponseDTO blocksReponseDTO = new BlocksReponseDTO();
        int i = checkTranslation(local, blocks.getBlockTranslations());
        if (i != -1) {
            blocksReponseDTO.setName(blocks.getBlockTranslations().get(i).getName());
            blocksReponseDTO.setImage(blocks.getBlockTranslations().get(i).getImage());
        } else {
            BlockTranslations blockTranslations = new BlockTranslations();
            blockTranslations.setBlocks(blocks);
            blockTranslations.setLocale(local);
            blockTranslations.setName(blocks.getBlockTranslations().get(0).getName());
            blockTranslations.setImage("");
            blockTranslationsRepository.save(blockTranslations);
            blocksReponseDTO.setName(blocks.getBlockTranslations().get(0).getName());
            blocksReponseDTO.setImage("");
        }
        blocksReponseDTO.setActive(blocks.getIsActive());
        blocksReponseDTO.setHtml(blocks.getHtml());
        blocksReponseDTO.setTagId(blocks.getTag().getId());
        List<BlockValuesReponseDTO> blockValuesReponseDTOS = blockValueConverter.listConverterBlockValues(local, blocks.getBlockValues());
        blocksReponseDTO.setBlockValues(blockValuesReponseDTOS);
        blocksReponseDTO.setId(blocks.getId());
        return blocksReponseDTO;
    }


    public List<BlocksReponseDTO> converterListBlockReponse(String local, List<Blocks> blocks) {
        List<BlocksReponseDTO> blocksReponseDTOS = new ArrayList<>();
        try {
            for (Blocks blocks1 : blocks
            ) {
                BlocksReponseDTO blocksReponseDTO = this.converterBlockReponse(local, blocks1);
                blocksReponseDTOS.add(blocksReponseDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return blocksReponseDTOS;
    }

    public List<BlocksReponseDTO> converterListBlockEditorAdd(String local, List<Blocks> blocks) {
        List<BlocksReponseDTO> blocksReponseDTOS = new ArrayList<>();
        try {
            for (Blocks blocks1 : blocks
            ) {
                BlocksReponseDTO blocksReponseDTO = this.converterBlockReponse(local, blocks1);
                blocksReponseDTOS.add(blocksReponseDTO);
            }

        } catch (Exception e) {
        }
        return blocksReponseDTOS;
    }

    public Blocks converterBlockToRequest(String local, BlockRequestDto blockRequestDto) {
        Blocks blocks = new Blocks();
        Tag tag = tagRepository.findById(blockRequestDto.getTagId()).get();
        blocks.setTag(tag);
        blocks.setHtml(blockRequestDto.getHtml());
        blocks.setIsActive(blockRequestDto.getActive());

        List<BlockTranslations> listblockTranslations = new ArrayList<>();
        BlockTranslations blockTranslations = new BlockTranslations();
        blockTranslations.setName(blockRequestDto.getName());
        blockTranslations.setLocale(local);
        blockTranslations.setImage(blockRequestDto.getImage());
        blockTranslations.setBlocks(blocks);
        listblockTranslations.add(blockTranslations);
        blocks.setBlockTranslations(listblockTranslations);

        List<BlockValueRequestDto> blockValueRequestDtos = blockRequestDto.getBlockValues();
        blocks.setCreated_at(new Date());
        List<BlockValues> blockValues = blockValueConverter.convertListDtoToEntity(local, blocks, blockValueRequestDtos);
        blocks.setBlockValues(blockValues);
        return blocks;
    }


    public int checkTranslation(String local, List<BlockTranslations> blockTranslations) {
        for (int i = 0; i < blockTranslations.size(); i++) {
            if (blockTranslations.get(i).getLocale().equalsIgnoreCase(local)) {
                return i;
            }
        }
        return -1;

    }


    public List<Blocks> getLocal(String local, List<Blocks> blocks) {
        List<Blocks> blocks1 = new ArrayList<>();

        return blocks1;
    }
}
