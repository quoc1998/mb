package com.backend.bank.converters.bases.converter;

import com.backend.bank.common.Constants;
import com.backend.bank.dto.request.CategoryRequest;
import com.backend.bank.dto.response.category.CategoryReponseDTO;
import com.backend.bank.dto.response.category.CategoryResponse;
import com.backend.bank.dto.response.category.CategoryResponseNotTeamDTO;
import com.backend.bank.dto.response.team.TeamDTO;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Category;
import com.backend.bank.model.CategoryTranslations;
import com.backend.bank.model.Team;
import com.backend.bank.repository.CategoryRepository;
import com.backend.bank.repository.CategoryTranslationsRepository;
import com.backend.bank.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryConverter {

    @Autowired
    NewConverter newConverter;

    @Autowired
    TeamConverter teamConverter;

    @Autowired
    CategoryTranslationsRepository categoryTranslationsRepository;
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public CategoryReponseDTO converterCategoryEntity(Category category, String local) {
        CategoryReponseDTO categoryReponseDTO = new CategoryReponseDTO();
        categoryReponseDTO.setId(category.getId());
        categoryReponseDTO.setName(category.getCategoryTranslations().get(0).getName());
        categoryReponseDTO.setIs_active(category.getIs_active());
        categoryReponseDTO.setNews(newConverter.converterListDto(local, category.getNews()));
        return categoryReponseDTO;
    }

    public List<CategoryReponseDTO> converterListCategoryEntity(List<Category> categories, String local) {
        List<CategoryReponseDTO> categoryReponseDTOS = new ArrayList<>();

        for (Category category : categories
        ) {
            CategoryReponseDTO categoryReponseDTO = converterCategoryEntity(category, local);
            categoryReponseDTOS.add(categoryReponseDTO);
        }
        return categoryReponseDTOS;
    }

    public List<CategoryResponse> converterListCategoryResponse(List<Category> categories, String local) {
        List<CategoryResponse> categoryReponseDTOS = new ArrayList<>();

        for (Category category : categories
        ) {
            CategoryResponse categoryResponse = entityConverterResponse(category, local);
            categoryReponseDTOS.add(categoryResponse);
        }
        return categoryReponseDTOS;
    }

    public List<CategoryResponseNotTeamDTO> converterListCategoryResponseNotTeam(List<Category> categories, String local) {
        List<CategoryResponseNotTeamDTO> categoryReponseDTOS = new ArrayList<>();

        for (Category category : categories
        ) {
            CategoryResponseNotTeamDTO categoryResponse = entityConverterResponseNotTeam(category, local);
            categoryReponseDTOS.add(categoryResponse);
        }
        return categoryReponseDTOS;
    }

    public CategoryResponseNotTeamDTO entityConverterResponseNotTeam(Category category, String local) {
        CategoryResponseNotTeamDTO categoryResponse = new CategoryResponseNotTeamDTO();
        CategoryTranslations categoryTranslations;
        Integer loca = checkTranslation(category.getId(), category.getCategoryTranslations(), local);
        if (loca != -1) {
            categoryTranslations = category.getCategoryTranslations().get(loca);
        } else {
            categoryTranslations = new CategoryTranslations();
            categoryTranslations.setDescription("");
            categoryTranslations.setLocale(local);
            categoryTranslations.setSlug(Constants.toSlug(category.getCategoryTranslations().get(0).getName()));
            categoryTranslations.setName(category.getCategoryTranslations().get(0).getName() + " " + local);
            categoryTranslations.setCategory(category);
            categoryTranslationsRepository.save(categoryTranslations);
        }
        categoryResponse.setSlug(categoryTranslations.getSlug());
        categoryResponse.setName(categoryTranslations.getName());
        categoryResponse.setDescription(categoryTranslations.getDescription());
        categoryResponse.setBase_image(category.getBase_image());
        if (category.getCategory() != null) {
            categoryResponse.setParentId(category.getCategory().getId());
        }
        categoryResponse.setId(category.getId());

        categoryResponse.setIs_active(category.getIs_active());
        categoryResponse.setPosition(category.getPosition());
        return categoryResponse;
    }

    public CategoryResponse entityConverterResponse(Category category, String local) {
        CategoryResponse categoryResponse = new CategoryResponse();
        Integer location = checkTranslation(category.getId(), category.getCategoryTranslations(), local);
        CategoryTranslations categoryTranslations;
        if (location != -1) {
            categoryTranslations = category.getCategoryTranslations().get(location);

        } else {
            categoryTranslations = new CategoryTranslations();
            categoryTranslations.setDescription("");
            categoryTranslations.setLocale(local);
            categoryTranslations.setSlug(Constants.toSlug(category.getCategoryTranslations().get(0).getName()));
            categoryTranslations.setName(category.getCategoryTranslations().get(0).getName() + " " + local);
            categoryTranslations.setCategory(category);
            categoryTranslationsRepository.save(categoryTranslations);
        }
        categoryResponse.setName(categoryTranslations.getName());
        categoryResponse.setDescription(categoryTranslations.getDescription());
        categoryResponse.setBase_image(category.getBase_image());
        categoryResponse.setTeams(teamConverter.converterListTeamDTO(category.getTeam(), local));
        if (category.getCategory() != null) {
            categoryResponse.setParentId(category.getCategory().getId());
        }
        categoryResponse.setId(category.getId());
        categoryResponse.setSlug(categoryTranslations.getSlug());
        categoryResponse.setIs_active(category.getIs_active());
        categoryResponse.setPosition(category.getPosition());
        return categoryResponse;
    }

    public Category converterToRequest(CategoryRequest categotyRequest, String local) {
        Category category = new Category();
        if (categotyRequest.getParentId() != null) {
            Category categoryParent = categoryRepository.findById(categotyRequest.getParentId()).orElse(null);
            if (categoryParent == null) {
                throw new NotFoundException("Not Found Category");
            }
            category.setCategory(categoryParent);
        }
        if (categotyRequest.getTeams().size() != 0) {
            List<Team> teams = teamRepository.findAllByTeamIds(convertListIdTeam(categotyRequest.getTeams()));
            category.setTeam(teams);
        }
        category.setPosition(categotyRequest.getPosition());
        category.setBase_image(categotyRequest.getBase_image());
        category.setIs_active(categotyRequest.getIs_active());
        CategoryTranslations categoryTranslations = new CategoryTranslations();
        categoryTranslations.setCategory(category);
        categoryTranslations.setLocale(local);
        categoryTranslations.setSlug(Constants.toSlug(categotyRequest.getName()));
        categoryTranslations.setName(categotyRequest.getName());
        categoryTranslations.setDescription(categotyRequest.getDescription());
        List<CategoryTranslations> categoryTranslationsList = new ArrayList<>();
        categoryTranslationsList.add(categoryTranslations);
        category.setCategoryTranslations(categoryTranslationsList);
        return category;
    }

    public Integer checkTranslation(Integer id, List<CategoryTranslations> categoryTranslations, String local) {
        Integer size = categoryTranslations.size();
        if (size.equals(0)) {
            throw new NotFoundException("Category not found translation: " + id);
        }
        for (Integer i = 0; i < size; i++) {
            if (local.equals(categoryTranslations.get(i).getLocale())) {
                return i;
            }
        }
        return -1;
    }

    public List<Integer> convertListTeamId(List<Team> teamList) {
        List<Integer> teamIds = new ArrayList<>();
        for (Team team : teamList
        ) {
            Integer id = team.getTeamId();
            teamIds.add(id);
        }
        return teamIds;
    }

    public List<Integer> convertListIdTeam(List<TeamDTO> teamList) {
        List<Integer> teamIds = new ArrayList<>();
        for (TeamDTO team : teamList
        ) {
            Integer id = team.getIdTeam();
            teamIds.add(id);
        }
        return teamIds;
    }

}
