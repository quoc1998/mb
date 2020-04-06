package com.backend.bank.converters.bases.converter;

import com.backend.bank.common.Constants;
import com.backend.bank.common.ImageResizer;
import com.backend.bank.converters.bases.converter.block.BlockConverter;
import com.backend.bank.converters.bases.converter.block.PageBlockConverter;
import com.backend.bank.converters.bases.converter.menu.MenuItemConverter;
import com.backend.bank.dto.request.PagesRequestDto;
import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.dto.response.pages.PageBlockReponseDto;
import com.backend.bank.dto.response.pages.PagesResponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.*;
import com.backend.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class PageConverter {
    @Autowired
    TeamConverter teamConverter;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    SlidersRepository slidersRepository;

    @Autowired
    private PagesRepository pagesRepository;

    @Autowired
    private PageTranslationRepository pageTranslationRepository;

    @Autowired
    private MenuItemsRepository menuItemsRepository;

    @Autowired
    BlockConverter blockConverter;

    @Autowired
    SliderConverter sliderConverter;

    @Autowired
    PageBlockConverter pageBlockConverter;

    @Autowired
    MenuItemConverter menuItemConverter;

    @Autowired
    MenuConverter menuConverter;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ImageResizer imageResizer;

    Calendar calendar = Calendar.getInstance();
    Date date = calendar.getTime();


    public PagesResponseDto convertEnityToDto(Pages pages, String local) {
        PagesResponseDto pagesResponseDto = new PagesResponseDto();
        pagesResponseDto.setId(pages.getId());
        pagesResponseDto.setBaseImage(pages.getBaseImage());
        pagesResponseDto.setHas_sidebar(pages.getHas_sidebar());
        pagesResponseDto.setIs_active(pages.getIs_active());
        pagesResponseDto.setPosition(pages.getPosition());
        pagesResponseDto.setTemplate(pages.getTemplate());
        pagesResponseDto.setSection(pages.getSection());
        pagesResponseDto.setParent_id(pages.getParentId());
        pagesResponseDto.setTeam(pages.getTeam().getTeamId());
        pagesResponseDto.setMiniImage(pages.getMiniImage());
        if (pages.getSliders() != null) {
            pagesResponseDto.setSliders(sliderConverter.SliderConverter(pages.getSliders(), local));
        }
        if (pages.getMenuMiddle() != null && pages.getMenuMiddle().getMenuItems().size() >  0) {
            pagesResponseDto.setMenuMiddle(menuConverter.converterMenuMiddle(pages.getMenuMiddle(), local));
        }

        Integer location = checkNewsTranslation(local, pages.getPageTranslations());
        PageTranslations pageTranslation;
        if (location != -1) {
            pageTranslation = pages.getPageTranslations().get(location);
        } else {
            pageTranslation = new PageTranslations();
            pageTranslation.setMeta_description("");
            pageTranslation.setLocale(local);
            pageTranslation.setMeta_keyword("");
            pageTranslation.setMeta_title("");
            pageTranslation.setName(pages.getPageTranslations().get(0).getName());
            pageTranslation.setSlug("");
            pageTranslation.setPages(pages);
            pageTranslationRepository.save(pageTranslation);
        }
        pagesResponseDto.setSlug(pageTranslation.getSlug());
        pagesResponseDto.setLocale(pageTranslation.getLocale());
        pagesResponseDto.setMeta_title(pageTranslation.getMeta_title());
        pagesResponseDto.setMeta_description(pageTranslation.getMeta_description());
        pagesResponseDto.setMeta_keyword(pageTranslation.getMeta_keyword());
        pagesResponseDto.setName(pageTranslation.getName());
        List<PageBlockReponseDto> pageBlockReponseDtos = pageBlockConverter.converterListEntity(local, pages.getPageBlocks());
        pagesResponseDto.setPageBlocks(pageBlockReponseDtos);
        List<MenuItems> menuItemsList = menuItemsRepository.findByPages(pages);
        List<MenuItemsReponseDto> menuItemsReponseDtos = menuItemConverter.converterListMenuItem(menuItemsList, local);
        pagesResponseDto.setMenuItems(menuItemsReponseDtos);
        return pagesResponseDto;
    }


    public List<PagesResponseDto> convertListEntityToDto(List<Pages> pages, String local) {
        List<PagesResponseDto> pagesResponseDtoList = new ArrayList<>();
        for (Pages pageItem : pages) {
            pagesResponseDtoList.add(this.convertEnityToDto(pageItem, local));
        }
        return pagesResponseDtoList;
    }

    public Pages convertDtoToEntity(PagesRequestDto pagesRequestDto, String local) {
        Pages pages = new Pages();
        pages.setBaseImage(pagesRequestDto.getBaseImage());
        pages.setHas_sidebar(pagesRequestDto.getHas_sidebar());
        pages.setIs_active(pagesRequestDto.getIs_active());
        pages.setPosition(pagesRequestDto.getPosition());
        pages.setTemplate(pagesRequestDto.getTemplate());
        pages.setSection(pagesRequestDto.getSection());
        pages.setMiniImage(pagesRequestDto.getMiniImage());
        if (pagesRequestDto.getMenuMiddleId() != -1) {
            Menu menu = menuRepository.findById(pagesRequestDto.getMenuMiddleId()).orElse(null);
            pages.setMenuMiddle(menu);
        } else {
            pages.setMenuMiddle(null);
        }

        if (pagesRequestDto.getSliderId() != null) {
            Sliders sliders = slidersRepository.findById(pagesRequestDto.getSliderId()).orElse(null);
            if (sliders == null) {
                throw new NotFoundException("Menu middle not found");
            }
            pages.setSliders(sliders);
        }

        Pages page = pagesRepository.findById(pagesRequestDto.getParent_id()).orElse(null);
        if (page == null && pagesRequestDto.getParent_id() != 1) {
            throw new NotFoundException("Page parent not found");
        }
        pages.setParentId(pagesRequestDto.getParent_id());
        if (pages.getParentId() == 1) {
            pages.setTeam(teamRepository.findAllByTeamId(pagesRequestDto.getTeams()).get(0));
        } else {
            pages.setTeam(pagesRepository.findById(pages.getParentId()).get().getTeam());
        }
        pages.setCreatedAt(date);
        pages.setParentId(pagesRequestDto.getParent_id());
        List<PageTranslations> pageTranslationsList = new ArrayList<>();
        List<PageBlocks> pageBlocks = pageBlockConverter.converterListRequest(local, pages, pagesRequestDto.getPageBlocks());
        pages.setPageBlocks(pageBlocks);

        PageTranslations pageTranslations = new PageTranslations();
        pageTranslations.setName(pagesRequestDto.getName());
        pageTranslations.setMeta_keyword(pagesRequestDto.getMeta_keyword());
        pageTranslations.setMeta_description(pagesRequestDto.getMeta_description());
        pageTranslations.setMeta_title(pagesRequestDto.getMeta_title());
        pageTranslations.setSlug(Constants.toSlug(pagesRequestDto.getName()));
        pageTranslations.setLocale(local);
        pageTranslations.setPages(pages);
        pageTranslationsList.add(pageTranslations);
        pages.setPageTranslations(pageTranslationsList);
        return pages;
    }

    public Integer checkNewsTranslation(String local, List<PageTranslations> pageTranslations) {
        Integer count = pageTranslations.size();
        if (count == 0) {
            throw new NotFoundException("Not Found News");
        }
        for (Integer i = 0; i < count; i++) {
            if (local.equals(pageTranslations.get(i).getLocale())) {
                return i;
            }

        }
        return -1;
    }
}
