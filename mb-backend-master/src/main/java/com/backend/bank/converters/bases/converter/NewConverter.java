package com.backend.bank.converters.bases.converter;

import com.backend.bank.common.Constants;
import com.backend.bank.common.ImageResizer;
import com.backend.bank.common.NewBlockSortPosition;
import com.backend.bank.converters.bases.converter.block.BlockConverter;
import com.backend.bank.dto.response.news.NewDTO;
import com.backend.bank.dto.request.CategoryNewsRequest;
import com.backend.bank.dto.request.NewsBlocksRequestDto;
import com.backend.bank.dto.response.news.NewsBlocksReponseDto;
import com.backend.bank.dto.response.news.NewsReponseDto;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.*;
import com.backend.bank.repository.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NewConverter {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BlocksRepository blocksRepository;

    @Autowired
    BlockConverter blockConverter;

    @Autowired
    NewsBlocksRepository newsBlocksRepository;

    @Autowired
    NewsBlockTranslationsRepository newsBlockTranslationsRepository;

    @Autowired
    NewsTranslationRepository newsTranslationRepository;

    @Autowired
    CategoryTranslationsRepository categoryTranslationsRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    ImageResizer imageResizer;

    public News converterEntity(String local, NewDTO newDTO) {
        List<Category> categories = new ArrayList<>();
        for (CategoryNewsRequest categoryNewsRequest : newDTO.getCategories()) {
            Optional<Category> category = categoryRepository.findById(categoryNewsRequest.getId());
            if (!category.isPresent()) {
                throw new NotFoundException("Category not found");
            } else {
                categories.add(category.get());
            }
        }
        //Calendar calendar = Calendar.getInstance();
        News news = new News();
        news.setCreatedAt(newDTO.getCreated_at());
        news.setIs_sticky(newDTO.getIs_sticky());
        news.setBase_image(newDTO.getBase_image());
        news.setAuthor_name(newDTO.getAuthor_name());
        news.setCategories(categories);
        news.setMiniImage(newDTO.getMiniImage());
        news.setNewsBlocks(this.converterNewListNewsBlock(local, news, newDTO.getNewsBlocks()));

        NewsTranslation newsTranslation = new NewsTranslation();
        newsTranslation.setTitle(newDTO.getTitle());
        newsTranslation.setLocale(local);
        String description = newDTO.getDescription() == null ? "description":  newDTO.getDescription();
        newsTranslation.setDescription(description);
        newsTranslation.setShortDescription(newDTO.getShortDescription());
        newsTranslation.setMeta_description(newDTO.getMeta_description());
        newsTranslation.setMeta_keyword(newDTO.getMeta_keyword());
        newsTranslation.setMeta_title(newDTO.getMeta_title());
        newsTranslation.setIsActive(newDTO.getIs_active());
        newsTranslation.setNews(news);
        newsTranslation.setUrl(Constants.toSlug(newDTO.getTitle()));
        List<NewsTranslation> newsTranslations = new ArrayList<>();
        newsTranslations.add(newsTranslation);
        news.setNewsTranslations(newsTranslations);
        return news;
    }

    public NewsBlocks converterNewBlockEntity(String local, News news, NewsBlocksRequestDto newsBlocksRequestDto) {
        NewsBlocks newsBlocks = new NewsBlocks();
        newsBlocks.setNews(news);
        Blocks blocks = blocksRepository.findById(newsBlocksRequestDto.getId_block()).get();
        newsBlocks.setBlocks(blocks);
        newsBlocks.setPosition(newsBlocksRequestDto.getPosition());
        List<NewsBlockTranslations> newsBlockTranslationsList = new ArrayList<>();
        NewsBlockTranslations newsBlockTranslations = new NewsBlockTranslations();
        newsBlockTranslations.setNewsBlock(newsBlocks);
        newsBlockTranslations.setContent(newsBlocksRequestDto.getContent());
        newsBlockTranslations.setTitle(newsBlocksRequestDto.getTitle());
        newsBlockTranslations.setLocale(local);
        newsBlockTranslationsList.add(newsBlockTranslations);
        newsBlocks.setNewsBlockTranslations(newsBlockTranslationsList);
        return newsBlocks;
    }

    public NewsBlocks converterNewBlockEntity2(String local, News news, NewsBlocksRequestDto newsBlocksRequestDto) {
        NewsBlocks newsBlocks;
        if (newsBlocksRequestDto.getId() == 0) {
            newsBlocks = this.converterNewBlockEntity(local, news, newsBlocksRequestDto);
        } else {
            newsBlocks = newsBlocksRepository.findById(newsBlocksRequestDto.getId()).get();
            Blocks blocks = blocksRepository.findById(newsBlocksRequestDto.getId_block()).get();
            newsBlocks.setBlocks(blocks);
            newsBlocks.setPosition(newsBlocksRequestDto.getPosition());
            NewsBlockTranslations newsBlockTranslations = this.getNewsBlockTranslations(local, newsBlocks.getNewsBlockTranslations());
            newsBlockTranslations.setContent(newsBlocksRequestDto.getContent());
            newsBlockTranslations.setTitle(newsBlocksRequestDto.getTitle());
            newsBlockTranslations.setLocale(local);
        }

        return newsBlocks;
    }

    public List<NewsBlocks> converterNewListNewsBlock(String local, News news, List<NewsBlocksRequestDto> newsBlocksRequestDtos) {
        List<NewsBlocks> newsBlocks = new ArrayList<>();
        for (NewsBlocksRequestDto newsBlocksRequestDto : newsBlocksRequestDtos) {
            NewsBlocks newsBlocks1 = this.converterNewBlockEntity(local, news, newsBlocksRequestDto);
            newsBlocks.add(newsBlocks1);
        }
        Collections.sort(newsBlocks, new NewBlockSortPosition());
        return newsBlocks;
    }

    public List<NewsBlocks> converterNewListNewsBlock2(String local, News news, List<NewsBlocksRequestDto> newsBlocksRequestDtos) {
        List<NewsBlocks> newsBlocks = new ArrayList<>();
        for (NewsBlocksRequestDto newsBlocksRequestDto : newsBlocksRequestDtos) {
            NewsBlocks newsBlocks1 = this.converterNewBlockEntity2(local, news, newsBlocksRequestDto);
            newsBlocks.add(newsBlocks1);
        }
        Collections.sort(newsBlocks, new NewBlockSortPosition());
        return newsBlocks;
    }

    public NewsBlocksReponseDto converterEntity(String local, NewsBlocks newsBlocks) {
        NewsBlocksReponseDto newsBlockReponseDto = new NewsBlocksReponseDto();
        newsBlockReponseDto.setId(newsBlocks.getId());
        newsBlockReponseDto.setId_news(newsBlocks.getNews().getId());
        newsBlockReponseDto.setBlocks(blockConverter.converterBlockReponse(local, newsBlocks.getBlocks()));

        NewsBlockTranslations newsBlockTranslations = this.getNewsBlockTranslations(local, newsBlocks.getNewsBlockTranslations());
        if (null == newsBlockTranslations) {
            newsBlockTranslations = new NewsBlockTranslations();
            newsBlockTranslations.setTitle(newsBlocks.getNewsBlockTranslations().get(0).getTitle());
            newsBlockTranslations.setLocale(local);
            newsBlockTranslations.setContent("");
            newsBlockTranslations.setNewsBlock(newsBlocks);
            newsBlockTranslationsRepository.save(newsBlockTranslations);

        }
        newsBlockReponseDto.setContent(newsBlockTranslations.getContent());
        newsBlockReponseDto.setTitle(newsBlockTranslations.getTitle());
        newsBlockReponseDto.setPosition(newsBlocks.getPosition());
        return newsBlockReponseDto;
    }

    public List<NewsBlocksReponseDto> converterListEntity(String local, List<NewsBlocks> newsBlocks) {
        List<NewsBlocksReponseDto> pageBlockReponseDtos = new ArrayList<>();
        for (NewsBlocks newsBlocks1 : newsBlocks
        ) {
            NewsBlocksReponseDto pageBlockReponseDto = this.converterEntity(local, newsBlocks1);
            pageBlockReponseDtos.add(pageBlockReponseDto);
        }
        return pageBlockReponseDtos;
    }

    public NewsReponseDto converterDto(String local, News news) {
        List<NewsTranslation> newsTranslations = newsTranslationRepository.findByNews(news);
        NewsReponseDto newsReponseDto = new NewsReponseDto();
        NewsTranslation newsTranslation;
        Integer location = checkNewsTranslation(local, news.getNewsTranslations());
        if (location != -1) {
            newsTranslation = news.getNewsTranslations().get(location);
        } else {
            newsTranslation = new NewsTranslation();
            newsTranslation.setLocale(local);
            newsTranslation.setIsActive(false);
            newsTranslation.setTitle(newsTranslations.get(0).getTitle());
            newsTranslation.setDescription(" ");
            newsTranslation.setShortDescription(" ");
            newsTranslation.setMeta_description(" ");
            newsTranslation.setMeta_keyword(" ");
            newsTranslation.setMeta_title("title");
            newsTranslation.setNews(news);
            newsTranslation.setUrl(" ");
            newsTranslationRepository.save(newsTranslation);
        }
        newsReponseDto.setAuthor_name(news.getAuthor_name());
        newsReponseDto.setBase_image(news.getBase_image());
        newsReponseDto.setMiniImage((news.getMiniImage()));
        newsReponseDto.setTitle(newsTranslation.getTitle());
        newsReponseDto.setIs_sticky(news.getIs_sticky());
        newsReponseDto.setCreated_at(news.getCreatedAt());
        newsReponseDto.setDescription(newsTranslation.getDescription());
        newsReponseDto.setShortDescription(newsTranslation.getShortDescription());
        if (news.getCategories() != null) {
            for (Category category : news.getCategories()) {
                CategoryNewsRequest categoryNewsRequest = new CategoryNewsRequest();
                categoryNewsRequest.setId(category.getId());
                if (categoryTranslationsRepository.findByCategoryAndLocale(category, local).isPresent()) {
                    categoryNewsRequest.setSlug(categoryTranslationsRepository.findByCategoryAndLocale(category, local).get().getSlug());
                    categoryNewsRequest.setName(categoryTranslationsRepository.findByCategoryAndLocale(category, local).get().getName());
                }
                newsReponseDto.getCategories().add(categoryNewsRequest);
            }
        }
        newsReponseDto.setMeta_description(newsTranslation.getMeta_description());
        newsReponseDto.setMeta_keyword(newsTranslation.getMeta_keyword());
        newsReponseDto.setMeta_title(newsTranslation.getMeta_title());
        newsReponseDto.setLocal(newsTranslation.getLocale());
        newsReponseDto.setNewsId(newsTranslation.getNews().getId());
        newsReponseDto.setUrl(newsTranslation.getUrl());
        newsReponseDto.setIs_active(newsTranslation.getIsActive());
        newsReponseDto.setCreated_at(news.getCreatedAt());
        newsReponseDto.setLocal(newsTranslation.getLocale());
        newsReponseDto.setCreatedByUserId(news.getCreatedByUserId());
        if (news.getApprovedByUserId() != null){
            newsReponseDto.setApprovedByUserId(news.getApprovedByUserId());
        }

        if (news.getEditByUserId() != null){
            newsReponseDto.setEditByUserId(news.getEditByUserId());
        }
        newsReponseDto.setNewsBlocks(this.converterListEntity(local, news.getNewsBlocks()));
        return newsReponseDto;
    }

    public List<NewsReponseDto> converterListDto(String local, List<News> news) {
        List<NewsReponseDto> newsResponse = new ArrayList<>();
        for (News news1 : news
        ) {
            NewsReponseDto newsReponseDto = this.converterDto(local, news1);
            newsResponse.add(newsReponseDto);
        }
        return newsResponse;
    }

    public NewsBlockTranslations getNewsBlockTranslations(String local, List<NewsBlockTranslations> newsTranslations) {
        for (NewsBlockTranslations newsTranslation : newsTranslations
        ) {
            if (newsTranslation.getLocale().equalsIgnoreCase(local)) {
                return newsTranslation;
            }
        }
        return null;
    }

    public Integer checkNewsTranslation(String local, List<NewsTranslation> newsTranslations) {
        Integer count = newsTranslations.size();
        if (count == 0) {
            throw new NotFoundException("Not Found News");
        }
        for (Integer i = 0; i < count; i++) {
            if (local.equalsIgnoreCase(newsTranslations.get(i).getLocale())) {
                return i;
            }

        }
        return -1;
    }

}
