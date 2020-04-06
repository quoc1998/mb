package com.backend.bank.converters.bases.converter.block;

import com.backend.bank.dto.response.block.BlockValuesReponseDTO;
import com.backend.bank.dto.request.BlockValueRequestDto;
import com.backend.bank.model.BlockValueTranslations;
import com.backend.bank.model.BlockValues;
import com.backend.bank.model.Blocks;
import com.backend.bank.model.Types;
import com.backend.bank.repository.BlockValueRepository;
import com.backend.bank.repository.BlockValueTranslationsRepository;
import com.backend.bank.repository.TypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BlockValueConverter {

    @Autowired
    BlockValueRepository blockValueRepository;

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    BlockValueTranslationsRepository blockValueTranslationsRepository;


    public List<BlockValues> convertListDtoToEntity(String local, Blocks blocks, List<BlockValueRequestDto> blockValueDTOS) {
        List<BlockValues> blockValuesList = new ArrayList<>();
        for (BlockValueRequestDto blockValueDTO : blockValueDTOS) {
            if (blockValueDTO.getId() == 0) {
                BlockValueTranslations blockValueTranslations = new BlockValueTranslations();
                BlockValues blockValues = new BlockValues();
                blockValues.setPosition(blockValueDTO.getPosition());
                if (blockValueDTO.getType_id() != null) {
                    Types types = typesRepository.findById(blockValueDTO.getType_id()).orElse(null);
                    blockValues.setTypes(types);
                }
                blockValues.setKeyValue(blockValueDTO.getKey());
                blockValues.setBlocks(blocks);

                List<BlockValueTranslations> blockValueTranslations1 = new ArrayList<>();

                blockValueTranslations.setBlockValue(blockValues);
                blockValueTranslations.setLocale(local);
                blockValueTranslations.setTitle(blockValueDTO.getTitle());
                blockValueTranslations.setBlockValue(blockValues);

                blockValueTranslations1.add(blockValueTranslations);
                blockValues.setBlockValueTranslations(blockValueTranslations1);
                blockValuesList.add(blockValues);
            } else {
                BlockValues blockValues = blockValueRepository.findById(blockValueDTO.getId()).get();
                blockValues.setKeyValue(blockValueDTO.getKey());
                if (blockValueDTO.getType_id() != null) {
                    Types types = typesRepository.findById(blockValueDTO.getType_id()).orElse(null);
                    blockValues.setTypes(types);
                }
                int i = checkTranslation(local, blockValues.getBlockValueTranslations());
                if (i != -1) {
                    blockValues.getBlockValueTranslations().get(i).setTitle(blockValueDTO.getTitle());
                }
                blockValues.setPosition(blockValueDTO.getPosition());

                blockValuesList.add(blockValues);
            }


        }
        return blockValuesList;
    }


    public BlockValuesReponseDTO BlockValueDTO(String local, BlockValues blockValues) {
        BlockValuesReponseDTO blockValuesReponseDTO = new BlockValuesReponseDTO();
        blockValuesReponseDTO.setId(blockValues.getId());
        blockValuesReponseDTO.setPosition(blockValues.getPosition());
        blockValuesReponseDTO.setBlock_id(blockValues.getBlocks().getId());
        blockValuesReponseDTO.setKey(blockValues.getKeyValue());
        int i = checkTranslation(local, blockValues.getBlockValueTranslations());
        if (i != -1) {
            blockValuesReponseDTO.setTitle(blockValues.getBlockValueTranslations().get(i).getTitle());
        } else {
            BlockValueTranslations blockValueTranslations = new BlockValueTranslations();
            blockValueTranslations.setBlockValue(blockValues);
            blockValueTranslations.setLocale(local);
            blockValueTranslations.setTitle(blockValues.getBlockValueTranslations().get(0).getTitle());
            blockValueTranslationsRepository.save(blockValueTranslations);
            blockValuesReponseDTO.setTitle(blockValueTranslations.getTitle());
        }
        blockValuesReponseDTO.setType_id(blockValues.getTypes().getId());
        return blockValuesReponseDTO;
    }

    public List<BlockValuesReponseDTO> listConverterBlockValues(String local, List<BlockValues> blockValues) {
        List<BlockValuesReponseDTO> blockValuesReponseDTOS = new ArrayList<>();
        for (BlockValues blockValue : blockValues
        ) {
            BlockValuesReponseDTO blockValuesReponseDTO = this.BlockValueDTO(local, blockValue);
            blockValuesReponseDTOS.add(blockValuesReponseDTO);
        }
        return blockValuesReponseDTOS;
    }

    public int checkTranslation(String local, List<BlockValueTranslations> blockTranslations) {
        for (int i = 0; i < blockTranslations.size(); i++) {
            if (blockTranslations.get(i).getLocale().equalsIgnoreCase(local)) {
                return i;
            }
        }
        return -1;

    }


}
