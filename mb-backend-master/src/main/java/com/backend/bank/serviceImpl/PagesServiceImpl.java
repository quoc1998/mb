package com.backend.bank.serviceImpl;


import com.backend.bank.common.Constants;
import com.backend.bank.common.ImageResizer;
import com.backend.bank.common.PageBlockSortPosition;
import com.backend.bank.common.PageSortByPosition;
import com.backend.bank.converters.bases.converter.PageConverter;
import com.backend.bank.converters.bases.converter.block.PageBlockConverter;

import com.backend.bank.dto.request.PagesRequestDto;
import com.backend.bank.dto.response.pages.PageBlockReponseDto;
import com.backend.bank.dto.response.pages.PagesResponseDto;
import com.backend.bank.dto.response.pages.PaginationPage;
import com.backend.bank.exception.BadRequestException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.*;
import com.backend.bank.repository.*;
import com.backend.bank.service.PagesService;
import org.aspectj.weaver.ast.Not;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class PagesServiceImpl implements PagesService {
    private static Logger LOGGER = LoggerFactory.getLogger(PagesServiceImpl.class);

    @Autowired
    private PagesRepository pagesRepository;

    @Autowired
    MenuItemsRepository menuItemsRepository;

    @Autowired
    SlidersRepository slidersRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    private PageTranslationRepository pageTranslationRepository;

    @Autowired
    private PageConverter pageConverter;

    @Autowired
    PageBlocksRepository pageBlocksRepository;

    @Autowired
    PageBlockConverter pageBlockConverter;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ImageResizer imageResizer;


    @Override
    public List<PagesResponseDto> getPages(String locale) {
        Constants.checkLocal(locale);

        List<Pages> pages = pagesRepository.findAllByDeletedAt(null);
        return pageConverter.convertListEntityToDto(pages, locale);

    }


    @Override
    public List<PagesResponseDto> findAllById(List<Integer> ids, String local) {
        List<Pages> pages = pagesRepository.findAllById(ids);
        for (Pages pages1 : pages
        ) {
            if (pages1.getDeletedAt() != null) {
                throw new NotFoundException("Not found Page: " + pages1.getId());
            }
        }
        return pageConverter.convertListEntityToDto(pages, local);
    }

    @Override
    public PaginationPage findAllByUser(String locale, Integer page, Integer number) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Page<Pages> pageAll = pagesRepository.findAllByUser(pageable, userName);
        List<PagesResponseDto> pagesResponse = pageConverter.convertListEntityToDto(pageAll.getContent(), locale);
        PaginationPage paginationPage = new PaginationPage();
        paginationPage.setPages(pagesResponse);
        paginationPage.setSize(pageAll.getTotalPages());
        return paginationPage;
    }

    @Override
    public PaginationPage findAllByIsActive(String locale, Integer page, Integer number, Boolean isActive) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Page<Pages> pageAll = pagesRepository.findAllByIsActive(pageable, userName, isActive);
        List<PagesResponseDto> pagesResponse = pageConverter.convertListEntityToDto(pageAll.getContent(), locale);
        PaginationPage paginationPage = new PaginationPage();
        paginationPage.setPages(pagesResponse);
        paginationPage.setSize(pageAll.getTotalPages());
        return paginationPage;
    }

    @Override
    public PaginationPage searchPages(String locale, Integer page, Integer number, Boolean isActive, String search) {
        if (page != 0) {
            page = page - 1;
        }
        Sort sort = Sort.by("created_at").descending();
        Pageable pageable = PageRequest.of(page, number, sort);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Page<Pages> pageAll = pagesRepository.searchPages(pageable, userName, isActive, search);
        List<PagesResponseDto> pagesResponse = pageConverter.convertListEntityToDto(pageAll.getContent(), locale);
        PaginationPage paginationPage = new PaginationPage();
        paginationPage.setPages(pagesResponse);
        paginationPage.setSize(pageAll.getTotalPages());
        return paginationPage;
    }

    @Override
    public PagesResponseDto getPageById(int id, String local) {
        Constants.checkLocal(local);
        Pages pages = pagesRepository.findByIdAndDeletedAt(id, null);
        if (pages == null) {
            throw new NotFoundException("Pages not found");
        }
        return pageConverter.convertEnityToDto(pages, local);

    }

    @Override
    public PagesResponseDto getPageByName(String name, String local) {
        Constants.checkLocal(local);
        List<PageTranslations> pageTranslations = pageTranslationRepository.findBySlugAndLocale(name, local);
        if (pageTranslations.size() == 0) {
            throw new NotFoundException("Page not found");
        }

        Pages pages = pageTranslations.get(0).getPages();
        if (pages.getDeletedAt() == null) {
            return pageConverter.convertEnityToDto(pages, local);
        }
        return null;
    }

    @Override
    public PagesResponseDto addPages(PagesRequestDto pagesRequestDto, String local) {
        Constants.checkLocal(local);
        Optional<PageTranslations> pageTranslations = pageTranslationRepository.findByNameAndLocale(pagesRequestDto.getName(), local);
        if (pageTranslations.isPresent()) {
            throw new BadRequestException("Page is existed");
        }
        Optional<Pages> pages = pagesRepository.findById(pagesRequestDto.getParent_id());
        if (pagesRequestDto.getName().equalsIgnoreCase("homepage")) {
            pagesRequestDto.setParent_id(0);
        } else if (!pages.isPresent()) {
            throw new NotFoundException("Page parent not found!");
        }
        List<PagesResponseDto> pagesResponseDtos = sortPage(pagesRequestDto.getParent_id(), local);
        if (pagesResponseDtos.size() == 0) {
            pagesRequestDto.setPosition(1);
        } else {
            pagesRequestDto.setPosition(pagesResponseDtos.get(pagesResponseDtos.size() - 1).getPosition() + 1);
        }
        Pages pagesNew = pageConverter.convertDtoToEntity(pagesRequestDto, local);
        pagesRepository.save(pagesNew);
        return pageConverter.convertEnityToDto(pagesNew, local);
    }

    @Override
    public int getIdHomePage(String local) {
        Optional<PageTranslations> pageTranslations = pageTranslationRepository.findByNameAndLocale("homepage", Constants.VI);
        if (!pageTranslations.isPresent()) {
            throw new NotFoundException("Home page not found");
        }
        return pageTranslations.get().getPages().getId();
    }

    @Override
    public List<PagesResponseDto> getPageList(List<Integer> ids, String local) {
        try {
            List<Pages> pages = pagesRepository.findAllById(ids);
            return pageConverter.convertListEntityToDto(pages, local);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<PagesResponseDto> getPageListByParent(Integer parent, String local) {
        try {
            List<Pages> pages = pagesRepository.findAllByParentId(parent);
            return pageConverter.convertListEntityToDto(pages, local);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PagesResponseDto editPages(int id, PagesRequestDto pagesRequestDto, String local) {
        Constants.checkLocal(local);
        Pages pages = pagesRepository.findById(id).orElse(null);
        if (pages == null) {
            throw new NotFoundException("Page not found");
        }


        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        pages.setParentId(pagesRequestDto.getParent_id());
        pages.setBaseImage(pagesRequestDto.getBaseImage());
        pages.setMiniImage(pagesRequestDto.getMiniImage());
        pages.setHas_sidebar(pagesRequestDto.getHas_sidebar());
        pages.setPosition(pagesRequestDto.getPosition());
        pages.setUpdatedAt(date);
        if (pagesRequestDto.getMenuMiddleId() != -1) {
            Menu menu = menuRepository.findById(pagesRequestDto.getMenuMiddleId()).get();
            pages.setMenuMiddle(menu);

        } else if (pagesRequestDto.getMenuMiddleId() == -1) {
            pages.setMenuMiddle(null);
        }
        if (pagesRequestDto.getSliderId() != null) {
            Sliders sliders = slidersRepository.findById(pagesRequestDto.getSliderId()).orElse(null);
            if (sliders == null) {
                throw new NotFoundException("Menu middle not found");
            }
            pages.setSliders(sliders);
        }
        pages.setTemplate(pagesRequestDto.getTemplate());
        List<PageBlocks> pageBlocks = pageBlockConverter.converterListRequest2(local, pages, pagesRequestDto.getPageBlocks());
        pages.setPageBlocks(pageBlocks);
        try {
            Team team = teamRepository.findByTeamIdAndLocalAndDeletedAt(pagesRequestDto.getTeams(), local, null).orElse(new Team());
            pages.setTeam(team);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer location = checkTranslation(local, pages.getPageTranslations());
        String slug = pages.getPageTranslations().get(location).getSlug();
        if (pagesRequestDto.getSlug().isEmpty()) {
            pages.getPageTranslations().get(location).setSlug(Constants.toSlug(pagesRequestDto.getName()));
        } else {
            pages.getPageTranslations().get(location).setSlug(pagesRequestDto.getSlug());
        }
        String slugNew = pages.getPageTranslations().get(location).getSlug();
        if (!slug.equalsIgnoreCase(slugNew)){
            //cập nhật menu Item
            List<MenuItems> menuItemsList = menuItemsRepository.findAllBySlugPages(slug);
            if (!menuItemsList.isEmpty()){
                for (MenuItems menuItems: menuItemsList
                     ) {
                    menuItems.setSlugPages(slugNew);
                    menuItemsRepository.save(menuItems);
                }
            }
        }
        pages.getPageTranslations().get(location).setName(pagesRequestDto.getName());
        pages.getPageTranslations().get(location).setMeta_title(pagesRequestDto.getMeta_title());
        pages.getPageTranslations().get(location).setMeta_description(pagesRequestDto.getMeta_description());
        pages.getPageTranslations().get(location).setMeta_keyword(pagesRequestDto.getMeta_keyword());
        pages.getPageTranslations().get(location).setPages(pages);
        pagesRepository.save(pages);
        return pageConverter.convertEnityToDto(pages, local);
    }


    @Override
    public void deletePages(int id) {
        Optional<Pages> pages = pagesRepository.findById(id);
        if (!pages.isPresent()) {
            throw new NotFoundException("Page not found!");
        }
        if (checkPageHome(pages.get().getPageTranslations())) {
            throw new BadRequestException("homepage could not be deleted");
        }
        pagesRepository.delete(pages.get());

    }

    @Override
    public PagesResponseDto changActivePage(int id, String locale) {
        Optional<Pages> pages = pagesRepository.findById(id);
        if (!pages.isPresent()) {
            throw new NotFoundException("Page not found");
        }
        if (pages.get().getIs_active() == Constants.ACCEPT) {
            pages.get().setIs_active(Constants.DENY);
        } else {
            pages.get().setIs_active(Constants.ACCEPT);
        }
        pagesRepository.save(pages.get());
        return pageConverter.convertEnityToDto(pages.get(), locale);
    }

    @Override
    public List<PagesResponseDto> updatePosition(int parentId, int pageId, int position, String local) {

        Constants.checkLocal(local);
        Optional<Pages> pages = pagesRepository.findById(parentId);
        if (parentId != 0 && !pages.isPresent()) {
            throw new NotFoundException("Page parent not found!");
        }

        Optional<Pages> page = pagesRepository.findById(pageId);
        if (!page.isPresent()) {
            throw new NotFoundException("Page not found!");
        }
        List<Pages> pagesList = pagesRepository.findByParentId(parentId);
        for (Pages pageItem : pagesList) {
            if (pageItem.getPosition() >= position) {
                int temp = pageItem.getPosition();
                pageItem.setPosition(temp + 1);
                pagesRepository.save(pageItem);
            }
        }
        page.get().setParentId(parentId);
        page.get().setPosition(position);
        // update slug
        Integer location = checkTranslation(local, page.get().getPageTranslations());
        String slug = page.get().getPageTranslations().get(location).getSlug();
        Integer locationParent = checkTranslation(local, pages.get().getPageTranslations());
        String slugParent = pages.get().getPageTranslations().get(locationParent).getSlug();
        List<String> slugTemp = Arrays.asList(slug.split("/"));
        StringBuilder slugNew = new StringBuilder();
        slug = slugTemp.get(slugTemp.size() - 1);
        slugNew.append(slugParent).append("/").append(slug);
        page.get().getPageTranslations().get(location).setSlug(slugNew.toString());
        // update slug MenuItem;
        List<MenuItems> menuItemsList = menuItemsRepository.findAllBySlugPages(slug);
        if (!menuItemsList.isEmpty()){
            for (MenuItems menuItems: menuItemsList
            ) {
                menuItems.setSlugPages(slugNew.toString());
                menuItemsRepository.save(menuItems);
            }
        }
        pagesRepository.save(page.get());
        return sortPage(parentId, local);
    }

    @Override
    public List<PageBlockReponseDto> updatePositions(int idPage, int idPageBlock, int position, String local) {
        List<PageBlocks> pageBlocks = pagesRepository.findById(idPage).get().getPageBlocks();
        PageBlocks pageBlock = pageBlocksRepository.findById(idPageBlock).orElse(null);
        if (pageBlock == null) {
            throw new NotFoundException("PageBlock not found");
        }
        for (PageBlocks pageBlockItem : pageBlocks) {
            if (pageBlockItem.getPosition() >= position) {
                if (pageBlock.getPosition() - 1 == pageBlockItem.getPosition()) {
                    int temp = pageBlockItem.getPosition();
                    pageBlockItem.setPosition(temp + 1);
                    pageBlocksRepository.save(pageBlockItem);
                    break;
                } else {
                    int temp = pageBlockItem.getPosition();
                    pageBlockItem.setPosition(temp + 1);
                    pageBlocksRepository.save(pageBlockItem);
                }

            }
        }
        pageBlock.setPosition(position);
        pageBlocksRepository.save(pageBlock);
        Collections.sort(pageBlocks, new PageBlockSortPosition());
        return pageBlockConverter.converterListEntity(local, pageBlocks);

    }

    @Override
    public void deletePageBlock(Integer idPage, Integer idPageBlock) {
        try {
            pageBlocksRepository.deleteById(idPageBlock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PagesResponseDto> sortPage(int parentId, String local) {

        Constants.checkLocal(local);
        Optional<Pages> pages = pagesRepository.findById(parentId);
        if (parentId != 0 && !pages.isPresent()) {
            throw new NotFoundException("Page parent not found!");
        }
        List<Pages> pagesList = pagesRepository.findByParentId(parentId);
        Collections.sort(pagesList, new PageSortByPosition());
        List<PagesResponseDto> pagesResponseDtos = pageConverter.convertListEntityToDto(pagesList, local);
        return pagesResponseDtos;

    }

    public Boolean checkPageHome(List<PageTranslations> listPageTranslations) {
        for (PageTranslations pageTranslations : listPageTranslations
        ) {
            if (pageTranslations.getName().equalsIgnoreCase("homepage")) {
                return true;
            }
        }
        return false;
    }

    public int checkTranslation(String local, List<PageTranslations> pageTranslations) {
        Integer size = pageTranslations.size();
        Integer location = -1;
        if (size == 0) {
            throw new NotFoundException("Pages not translations");
        }
        for (int i = 0; i < size; i++) {
            if (pageTranslations.get(i).getLocale().equalsIgnoreCase(local)) {
                location = i;
            }
        }
        return location;
    }

}
