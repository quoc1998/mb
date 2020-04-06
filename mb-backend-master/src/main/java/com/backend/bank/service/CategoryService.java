package com.backend.bank.service;

import com.backend.bank.dto.request.CategoryRequest;
import com.backend.bank.dto.response.category.CategoryResponse;
import com.backend.bank.dto.response.category.CategoryResponseNotTeamDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {
    List<CategoryResponse> getAllCategory(String local);
    List<CategoryResponse> getAllFrontendCategory(String local);
    List<CategoryResponseNotTeamDTO> findAllByUsername(String local);
    CategoryResponse addCategory(CategoryRequest categoryDTO, String local);
    CategoryResponse editCategory(int id, CategoryRequest categoryDTO, String local);
    CategoryResponse changeActiveCategory(int id,String local);
    List<CategoryResponse> updatePosition(String local,Integer parentId,int categoryId,int position);
    List<CategoryResponse> sortCategory(int parentId,String local);
    Boolean deleteCategory(int id);
    CategoryResponse getCategoryById(int id , String local);
    Boolean deleteIds(List<Integer> ids);
    CategoryResponse getFrontendCategoryById(int id, String local);
}
