package com.backend.bank.controller.fontend;

import com.backend.bank.dto.response.category.CategoryResponse;
import com.backend.bank.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/fe/categorys")
public class CategoryFrontendController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public List<CategoryResponse> getAllCategory(@PathVariable("local") String local) {
        return categoryService.getAllFrontendCategory(local);
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable("local") String local, @PathVariable("id") int id) {
        return categoryService.getFrontendCategoryById(id, local);
    }

    @GetMapping("/sort/{id}")
    public List<CategoryResponse> sortCategory(@PathVariable("local") String local, @PathVariable("id") int id) {
        return categoryService.sortCategory(id, local);
    }

}
