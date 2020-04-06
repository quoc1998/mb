package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.converters.bases.converter.block.BlockConverter;
import com.backend.bank.converters.bases.converter.block.BlockValueConverter;
import com.backend.bank.dto.response.block.BlocksReponseDTO;
import com.backend.bank.dto.request.BlockRequestDto;
import com.backend.bank.dto.response.block.PaginationBlock;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.BlockTranslations;
import com.backend.bank.model.Blocks;
import com.backend.bank.model.Tag;
import com.backend.bank.repository.BlockTranslationsRepository;
import com.backend.bank.repository.BlocksRepository;
import com.backend.bank.repository.TagRepository;
import com.backend.bank.service.BlocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BlocksServiceImpl implements BlocksService {
    @Autowired
    private BlocksRepository blocksRepository;

    @Autowired
    private BlockTranslationsRepository blockTranslationsRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    BlockConverter blockConverter;

    @Autowired
    BlockValueConverter blockValueConverter;


    @Override
    public List<BlocksReponseDTO> getBlocks(String local) {
        Constants.checkLocal(local);
        List<Blocks> blocks = blocksRepository.findAll();
        List<BlocksReponseDTO> blocksReponseDTOS = blockConverter.converterListBlockReponse(local, blocks);
        return blocksReponseDTOS;
    }

    @Override
    public PaginationBlock findAllPagin(String locale, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Blocks> pageAll = blocksRepository.getAllBy(pageable);
        List<BlocksReponseDTO> pagesResponseDtos = blockConverter.converterListBlockEditorAdd(locale, pageAll.getContent());
        PaginationBlock paginationBlock = new PaginationBlock();
        paginationBlock.setBlocks(pagesResponseDtos);
        paginationBlock.setSize(pageAll.getTotalPages());
        return paginationBlock;
    }

    @Override
    public PaginationBlock getBlocksSearch(String locale, Integer page, Integer number, String search) {
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Page<Blocks> pageAll = blocksRepository.getBlocksSearch(pageable, locale, search);
        List<BlocksReponseDTO> pagesResponseDtos = blockConverter.converterListBlockEditorAdd(locale, pageAll.getContent());
        PaginationBlock paginationBlock = new PaginationBlock();
        paginationBlock.setBlocks(pagesResponseDtos);
        paginationBlock.setSize(pageAll.getTotalPages());
        return paginationBlock;
    }

    @Override
    public BlocksReponseDTO findById(String local, Integer id) {
        Constants.checkLocal(local);
        try {
            Blocks blocks = blocksRepository.findById(id).orElse(null);
            if (blocks != null) {
                BlocksReponseDTO blocksReponseDTO = new BlocksReponseDTO();
                blocksReponseDTO.setActive(blocks.getIsActive());
                int i = blockConverter.checkTranslation(local, blocks.getBlockTranslations());
                if (i != -1) {
                    BlockTranslations blockTranslations = blocks.getBlockTranslations().get(i);
                    blocksReponseDTO.setName(blockTranslations.getName());
                } else {
                    BlockTranslations blockTranslations = new BlockTranslations();
                    blockTranslations.setName("");
                    blockTranslations.setLocale(local);
                    blockTranslations.setBlocks(blocks);
                    blockTranslationsRepository.save(blockTranslations);
                    blocksReponseDTO.setName(blockTranslations.getName());
                }
                blocksReponseDTO.setHtml(blocks.getHtml());
                blocksReponseDTO.setTagId(blocks.getTag().getId());
                blocksReponseDTO.setId(blocks.getId());
                return blocksReponseDTO;
            } else {
                throw new NotFoundException("Not found Block");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not found Block");
        }
    }

    @Override
    public BlocksReponseDTO addBlock(String local, BlockRequestDto blocksDTO) {
        Blocks blocks = blockConverter.converterBlockToRequest(local, blocksDTO);
        blocksRepository.save(blocks);
        BlocksReponseDTO blocksReponseDTO = blockConverter.converterBlockReponse(local, blocks);
        return blocksReponseDTO;
    }

    @Override
    public BlocksReponseDTO editBlock(int id, String local, BlockRequestDto blocksDTO) {
        Constants.checkLocal(local);
        try {
            BlockTranslations blockTranslations = blockTranslationsRepository.findByLocaleAndAndBlocks_Id(local, id);
            if (blockTranslations != null) {
                Blocks blocks = blockTranslations.getBlocks();
                blocks.setIsActive(blocksDTO.getActive());
                blocks.setHtml(blocksDTO.getHtml());
                if (blocksDTO.getTagId() != null) {
                    Tag tag = tagRepository.findById(blocksDTO.getTagId()).get();
                    blocks.setTag(tag);
                }
                blockTranslations.setName(blocksDTO.getName());

                blocks.setUpdatedAt(new Date());
                blocks.setBlockValues(blockValueConverter.convertListDtoToEntity(local, blocks, blocksDTO.getBlockValues()));
                blocksRepository.save(blocks);
                blockTranslationsRepository.save(blockTranslations);

                return blockConverter.converterBlockReponse(local, blocks);

            } else {
                throw new NotFoundException("Not found Block");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new NotFoundException("Not found Block");
        }
    }

    @Override
    public void deleteBlock(int id, String local) {
        Constants.checkLocal(local);
        try {
            BlockTranslations blockTranslations = blockTranslationsRepository.findByLocaleAndAndBlocks_Id(local, id);
            if (blockTranslations != null) {
                blockTranslationsRepository.deleteById(blockTranslations.getId());
            } else {
                throw new NotFoundException("Not found Block ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
