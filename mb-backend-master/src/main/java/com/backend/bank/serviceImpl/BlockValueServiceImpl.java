package com.backend.bank.serviceImpl;

import com.backend.bank.converters.bases.converter.block.BlockConverter;
import com.backend.bank.converters.bases.converter.block.BlockValueConverter;
import com.backend.bank.dto.request.BlockValueRequestDto;
import com.backend.bank.dto.response.block.BlockValuesReponseDTO;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.BlockValueTranslations;
import com.backend.bank.model.BlockValues;
import com.backend.bank.model.Blocks;
import com.backend.bank.model.Types;
import com.backend.bank.repository.BlockValueRepository;
import com.backend.bank.repository.BlockValueTranslationsRepository;
import com.backend.bank.repository.BlocksRepository;
import com.backend.bank.repository.TypesRepository;
import com.backend.bank.service.BlockValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlockValueServiceImpl implements BlockValueService {
    @Autowired
    private BlockValueRepository blockValueRepository;

    @Autowired
    BlockValueTranslationsRepository blockValueTranslationsRepository;

    @Autowired
    BlockValueConverter blockValueConverter;

    @Autowired
    TypesRepository typesRepository;

    @Autowired
    BlockConverter blockConverter;

    @Autowired
    BlocksRepository blocksRepository;


    @Override
    public List<BlockValuesReponseDTO> getAllBlockValues(String local, Integer blockId) {
        List<BlockValues> blockValues = blockValueRepository.findAllByBlocks_Id(blockId);
        return blockValueConverter.listConverterBlockValues(local, blockValues);
    }

    @Override
    public BlockValuesReponseDTO addBlockValue(String local, Integer blockId, BlockValueRequestDto blockValueDTO) {
        BlockValues blockValues = new BlockValues();
        List<BlockValueTranslations> blockValueTranslationsList = new ArrayList<>();
        BlockValueTranslations blockValueTranslations = new BlockValueTranslations();

        blockValues.setKeyValue(blockValueDTO.getKey());
        if (blockValueDTO.getType_id() != null) {
            Types types = typesRepository.findById(blockValueDTO.getType_id()).orElse(null);
            blockValues.setTypes(types);
        }
        Blocks blocks = blocksRepository.findById(blockId).get();
        blockValues.setBlocks(blocks);
        blockValues.setPosition(blockValueDTO.getPosition());
        blockValueTranslations.setBlockValue(blockValues);
        blockValueTranslations.setLocale(local);
        blockValueTranslations.setTitle(blockValueDTO.getTitle());
        blockValueTranslations.setBlockValue(blockValues);
        blockValueTranslationsList.add(blockValueTranslations);
        blockValues.setBlockValueTranslations(blockValueTranslationsList);
        blockValueRepository.save(blockValues);

        return blockValueConverter.BlockValueDTO(local, blockValues);
    }


    @Override
    public BlockValuesReponseDTO findById(String local, Integer idBlock, Integer idBlockValue) {
        return blockValueConverter.BlockValueDTO(local, blockValueRepository.findByIdAndBlocks_Id(idBlockValue, idBlock));
    }

    @Override
    public BlockValuesReponseDTO editBlockValue(String local, Integer id, Integer idBlockValue, BlockValueRequestDto blockValueDTO) {

        try {
            BlockValues blockValues = blockValueRepository.findByIdAndBlocks_Id(idBlockValue, id);
            blockValues.setPosition(blockValueDTO.getPosition());
            if (blockValueDTO.getType_id() != null) {
                Types types = typesRepository.findById(blockValueDTO.getType_id()).get();
                blockValues.setTypes(types);
            }
            blockValues.setKeyValue(blockValueDTO.getKey());
            int i = checkTranslation(local, blockValues.getBlockValueTranslations());

            blockValues.getBlockValueTranslations().get(i).setTitle(blockValueDTO.getTitle());
            blockValueRepository.save(blockValues);
            return blockValueConverter.BlockValueDTO(local, blockValues);
        } catch (Exception e) {
            throw new NotFoundException("Not found BlockValue");
        }

    }

    @Override
    public void deleteBlockValue(String local, Integer idBlock, Integer idBlockValue) {
        try {
            blockValueRepository.deleteById(idBlockValue);
        } catch (Exception e) {
            throw new NotFoundException(("not found block value"));
        }
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
