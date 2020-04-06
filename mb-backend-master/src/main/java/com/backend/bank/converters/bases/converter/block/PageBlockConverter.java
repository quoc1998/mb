package com.backend.bank.converters.bases.converter.block;

import com.backend.bank.common.PageBlockSortPosition;
import com.backend.bank.dto.request.PageBlockRequestDto;
import com.backend.bank.dto.response.pages.PageBlockReponseDto;
import com.backend.bank.model.PageBlockTranslations;
import com.backend.bank.model.PageBlocks;
import com.backend.bank.model.Pages;
import com.backend.bank.repository.BlocksRepository;
import com.backend.bank.repository.PageBlockTranslationsRepository;
import com.backend.bank.repository.PageBlocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PageBlockConverter {
    @Autowired
    BlockConverter blockConverter;

    @Autowired
    BlocksRepository blocksRepository;

    @Autowired
    PageBlocksRepository pageBlocksRepository;

    @Autowired
    PageBlockTranslationsRepository pageBlockTranslationsRepository;

    public PageBlockReponseDto converterEntity(String local, PageBlocks pageBlocks) {
        PageBlockReponseDto pageBlockReponseDto = new PageBlockReponseDto();
        pageBlockReponseDto.setId(pageBlocks.getId());
        pageBlockReponseDto.setId_page(pageBlocks.getPage().getId());
        pageBlockReponseDto.setBlocks(blockConverter.converterBlockReponse(local, pageBlocks.getBlocks()));
        int i = checkTranslation(local, pageBlocks.getPageBlockTranslations());
        if (i != -1) {
            pageBlockReponseDto.setContent(pageBlocks.getPageBlockTranslations().get(i).getContent());
            pageBlockReponseDto.setTitle(pageBlocks.getPageBlockTranslations().get(i).getTitle());
            pageBlockReponseDto.setName(pageBlocks.getPageBlockTranslations().get(i).getName());
            pageBlockReponseDto.setContentHtml(pageBlocks.getPageBlockTranslations().get(i).getContentHtml());
        } else {
            PageBlockTranslations pageBlockTranslations = new PageBlockTranslations();
            pageBlockTranslations.setLocale(local);
            pageBlockTranslations.setContentHtml("");
            pageBlockTranslations.setPageBlock(pageBlocks);
            pageBlockTranslations.setTitle(pageBlocks.getPageBlockTranslations().get(0).getTitle());
            pageBlockTranslations.setContent("");
            pageBlockTranslations.setName(pageBlocks.getPageBlockTranslations().get(0).getName());
            pageBlockTranslationsRepository.save(pageBlockTranslations);
            pageBlockReponseDto.setContent(pageBlockTranslations.getContent());
            pageBlockReponseDto.setTitle(pageBlockTranslations.getTitle());
            pageBlockReponseDto.setContentHtml(pageBlockTranslations.getContentHtml());

        }
        pageBlockReponseDto.setPosition(pageBlocks.getPosition());
        return pageBlockReponseDto;
    }

    public List<PageBlockReponseDto> converterListEntity(String local, List<PageBlocks> pageBlocks) {
        List<PageBlockReponseDto> pageBlockReponseDtos = new ArrayList<>();
        for (PageBlocks pageBlocks1 : pageBlocks
        ) {
            PageBlockReponseDto pageBlockReponseDto = this.converterEntity(local, pageBlocks1);
            pageBlockReponseDtos.add(pageBlockReponseDto);
        }
        return pageBlockReponseDtos;
    }


    public PageBlocks converterRequest(String local, Pages pages, PageBlockRequestDto pageBlockRequestDto) {
        PageBlocks pageBlocks = new PageBlocks();
        pageBlocks.setPosition(pageBlockRequestDto.getPosition());
        pageBlocks.setBlocks(blocksRepository.findById(pageBlockRequestDto.getId_block()).get());
        pageBlocks.setPage(pages);
        List<PageBlockTranslations> pageBlockTranslationsList = new ArrayList<>();
        PageBlockTranslations pageBlockTranslations = new PageBlockTranslations();
        pageBlockTranslations.setContent(pageBlockRequestDto.getContent());
        pageBlockTranslations.setLocale(local);
        pageBlockTranslations.setContentHtml(pageBlockRequestDto.getContentHtml());
        pageBlockTranslations.setTitle(pageBlockRequestDto.getTitle());
        pageBlockTranslations.setName(pageBlockRequestDto.getName());
        pageBlockTranslations.setPageBlock(pageBlocks);
        pageBlockTranslationsList.add(pageBlockTranslations);
        pageBlocks.setPageBlockTranslations(pageBlockTranslationsList);
        return pageBlocks;
    }

    public List<PageBlocks> converterListRequest(String local, Pages pages, List<PageBlockRequestDto> pageBlockRequestDto) {
        List<PageBlocks> pageBlocksList = new ArrayList<>();


        for (PageBlockRequestDto pageBlockDto : pageBlockRequestDto
        ) {
            PageBlocks pageBlocks = this.converterRequest(local, pages, pageBlockDto);
            pageBlocksList.add(pageBlocks);

        }
        Collections.sort(pageBlocksList, new PageBlockSortPosition());
        return pageBlocksList;
    }

    public List<PageBlocks> converterListRequest2(String local, Pages pages, List<PageBlockRequestDto> pageBlockRequestDto) {
        List<PageBlocks> pageBlocksList = new ArrayList<>();


        for (PageBlockRequestDto pageBlockDto : pageBlockRequestDto
        ) {
            if (pageBlockDto.getId() == 0) {
                PageBlocks pageBlocks = this.converterRequest(local, pages, pageBlockDto);
                pageBlocksList.add(pageBlocks);
            } else {
                PageBlocks pageBlocks = pageBlocksRepository.findById(pageBlockDto.getId()).get();
                pageBlocks.setPosition(pageBlockDto.getPosition());
                int i = checkTranslation(local, pageBlocks.getPageBlockTranslations());

                pageBlocks.getPageBlockTranslations().get(i).setContent(pageBlockDto.getContent());
                pageBlocks.getPageBlockTranslations().get(i).setName(pageBlockDto.getContent());
                pageBlocks.getPageBlockTranslations().get(i).setTitle(pageBlockDto.getTitle());
                pageBlocks.getPageBlockTranslations().get(i).setName(pageBlockDto.getName());
                pageBlocks.getPageBlockTranslations().get(i).setContentHtml(pageBlockDto.getContentHtml());
                pageBlocksList.add(pageBlocks);
            }
        }
        return pageBlocksList;
    }


    public int checkTranslation(String local, List<PageBlockTranslations> blockTranslations) {
        for (int i = 0; i < blockTranslations.size(); i++) {
            if (blockTranslations.get(i).getLocale().equalsIgnoreCase(local)) {
                return i;
            }
        }
        return -1;

    }


}
