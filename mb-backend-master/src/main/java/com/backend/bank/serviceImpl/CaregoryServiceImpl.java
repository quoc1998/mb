package com.backend.bank.serviceImpl;

import com.backend.bank.common.Constants;
import com.backend.bank.common.SortByPosition;
import com.backend.bank.converters.bases.converter.CategoryConverter;
import com.backend.bank.dto.request.CategoryRequest;
import com.backend.bank.dto.response.category.CategoryResponse;
import com.backend.bank.dto.response.category.CategoryResponseNotTeamDTO;
import com.backend.bank.dto.response.team.TeamDTO;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Category;
import com.backend.bank.model.CategoryTranslations;
import com.backend.bank.model.News;
import com.backend.bank.repository.CategoryRepository;
import com.backend.bank.repository.CategoryTranslationsRepository;
import com.backend.bank.repository.NewsRepository;
import com.backend.bank.repository.TeamRepository;
import com.backend.bank.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CaregoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    CategoryConverter categoryConverter;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<CategoryResponse> getAllCategory(String local) {
        Constants.checkLocal(local);
        List<Category> categories = categoryRepository.findAll();
        return categoryConverter.converterListCategoryResponse(categories, local);
    }

    @Override
    public List<CategoryResponse> getAllFrontendCategory(String local) {
        Constants.checkLocal(local);
        List<Category> categories = categoryRepository.findAllFrontend();
        return categoryConverter.converterListCategoryResponse(categories, local);
    }

    @Override
    public List<CategoryResponseNotTeamDTO> findAllByUsername(String local) {
        Constants.checkLocal(local);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<Category> categories = categoryRepository.findAllByUsername(userName);
        return categoryConverter.converterListCategoryResponseNotTeam(categories, local);
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest, String local) {
        Constants.checkLocal(local);
        Category category = categoryConverter.converterToRequest(categoryRequest, local);
        categoryRepository.save(category);
        return categoryConverter.entityConverterResponse(category, local);
    }

    @Override
    public CategoryResponse editCategory(int id, CategoryRequest CategoryRequest, String local) {
        Constants.checkLocal(local);
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new NotFoundException("Category not found");
        }
        Category category = categoryOptional.get();
        category.setTeam(teamRepository.findAllByTeamIds(convertListIdTeam(CategoryRequest.getTeams())));
        category.setBase_image(CategoryRequest.getBase_image());

        Integer location = checkTranslation(category.getCategoryTranslations(), local);
        if (location != -1){
            category.getCategoryTranslations().get(location).setSlug(Constants.toSlug(CategoryRequest.getName()));
            category.getCategoryTranslations().get(location).setDescription(CategoryRequest.getDescription());
            category.getCategoryTranslations().get(location).setName(CategoryRequest.getName());
        } else {
            CategoryTranslations categoryTranslations = new CategoryTranslations();
            categoryTranslations.setName(CategoryRequest.getName());
            categoryTranslations.setSlug(Constants.toSlug(CategoryRequest.getName()));
            categoryTranslations.setDescription(CategoryRequest.getDescription());
            categoryTranslations.setLocale(local);
            categoryTranslations.setCategory(category);
            category.getCategoryTranslations().add(categoryTranslations);
        }
        categoryRepository.save(category);
        return categoryConverter.entityConverterResponse(category, local);
    }

    @Override
    public CategoryResponse changeActiveCategory(int id, String local) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (!categoryOptional.isPresent()) {
            throw new NotFoundException("Category not found");
        }
        Category category = categoryOptional.get();
        if (category.getIs_active() == Constants.ACCEPT) {
            category.setIs_active(Constants.DENY);
        } else {
            category.setIs_active(Constants.ACCEPT);
        }
        categoryRepository.save(category);
        return categoryConverter.entityConverterResponse(category, local);
    }

    @Override
    public List<CategoryResponse> updatePosition(String local, Integer parentId, int categoryId, int position) {
        Constants.checkLocal(local);
        if (parentId == -1) {
            parentId = null;
        }
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (!category.isPresent()) {
            throw new NotFoundException("Category cot found!");
        }
        if (parentId != null) {
            Optional<Category> categoryParent = categoryRepository.findById(parentId);
            if (!categoryParent.isPresent()) {
                throw new NotFoundException("Category parent not found!");
            }

            List<Category> categories = category.get().getCategorys();
            for (Category category2 : categories) {
                if (category2.getPosition() >= position) {
                    int temp = category2.getPosition();
                    category2.setPosition(temp + 1);
                    categoryRepository.save(category2);
                }
            }
            category.get().setCategory(categoryParent.get());
            category.get().setPosition(position);
            categoryRepository.save(category.get());
        } else {
            List<Category> categories = categoryRepository.findAllByCategory(null);
            for (Category categoryItem : categories) {
                if (categoryItem.getPosition() >= position) {
                    int temp = categoryItem.getPosition();
                    categoryItem.setPosition(temp + 1);
                    categoryRepository.save(categoryItem);
                }
            }
            category.get().setCategory(null);
            category.get().setPosition(position);
            categoryRepository.save(category.get());
            categories.add(category.get());
            Collections.sort(categories, new SortByPosition());
            return categoryConverter.converterListCategoryResponse(categories, local);
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(int id) {
        try {
            Category categoryRoot = categoryRepository.findByCategoryByName("category_root");
            if (categoryRoot.getId() == id) {
                throw new NotFoundException("Not delete Category Root");
            }
            Optional<Category> category = categoryRepository.findById(id);
            if (!category.isPresent()) {
                throw new NotFoundException("Category not found!");
            }
            category.get().setDeletedAt(new Date());
            category.get().setTeam(null);
            List<News> newsList = category.get().getNews();
            if (newsList.size() > 0) {
                for (News news : newsList
                ) {
                    if (news.getCategories().size() == 1) {
                        List<Category> categoryList = new ArrayList<>();

                        categoryList.add(categoryRoot);
                        news.setCategories(categoryList);
                        newsRepository.save(news);
                    } else {
                        List<Category> categoryList = news.getCategories();
                        Integer location = checkLocationCategory(categoryList, id);
                        categoryList.remove(category.get());
                        news.setCategories(categoryList);
                        newsRepository.save(news);
                    }
                }
            }
            categoryRepository.save(category.get());
            return true;
        } catch (Exception e) {
            throw new NotFoundException("Category not found!");
        }
    }

    @Override
    public CategoryResponse getCategoryById(int id, String local) {
        Constants.checkLocal(local);
        Optional<Category> category = categoryRepository.findById(id);
        if (!category.isPresent()) {
            throw new NotFoundException("Category not found!");
        }
        return categoryConverter.entityConverterResponse(category.get(), local);
    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean result;
        try {
            for (Integer id : ids
            ) {
                deleteCategory(id);
            }
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    @Override
    public CategoryResponse getFrontendCategoryById(int id, String local) {
        Constants.checkLocal(local);
        Category category = categoryRepository.findFrontendById(id);
        if (category == null) {
            throw new NotFoundException("Category not found!");
        }
        return categoryConverter.entityConverterResponse(category, local);
    }

    public List<CategoryResponse> sortCategory(int parentId, String local) {
        Constants.checkLocal(local);
        Optional<Category> category = categoryRepository.findById(parentId);
        if (parentId != 0 && !category.isPresent()) {
            throw new NotFoundException("Category parent not found!");
        }
        List<Category> categories = category.get().getCategorys();
        Collections.sort(categories, new SortByPosition());
        return categoryConverter.converterListCategoryResponse(categories, local);
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

    public Integer checkLocationCategory(List<Category> categoryList, Integer idCategory) {
        Integer location = 0;
        Integer size = categoryList.size();
        for (Integer i = 0; i < size; i++) {
            if (categoryList.get(i).getId() == idCategory) {
                location = i;
            }
        }
        return location;
    }

    public Integer checkTranslation(List<CategoryTranslations> categoryTranslations, String local){
        Integer size = categoryTranslations.size();
        if (size == 0){
            throw new NotFoundException("Category not found translation");
        }
        for (Integer i = 0; i < size; i++) {
            if (categoryTranslations.get(i).getLocale().equalsIgnoreCase(local)){
                return i;
            }
        }
        return -1;
    }
}
