package com.backend.bank.controller;

import com.backend.bank.dto.request.CategoryRequest;
import com.backend.bank.dto.response.category.CategoryResponse;
import com.backend.bank.dto.response.category.CategoryResponseNotTeamDTO;
import com.backend.bank.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{local}/api/categorys")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Secured({"ROLE_GET CATEGORY", "ROLE_XEM DANH MỤC"})
    @GetMapping
    public List<CategoryResponse> getAllCategory(@PathVariable("local") String local) {
        return categoryService.getAllCategory(local);
    }

    @Secured({"ROLE_GET CATEGORY", "ROLE_XEM DANH MỤC"})
    @GetMapping("/findbyuser")
    public List<CategoryResponseNotTeamDTO> findAllByUsername(@PathVariable("local") String local) {
        return categoryService.findAllByUsername(local);
    }

    @Secured({"ROLE_GET CATEGORY", "ROLE_XEM DANH MỤC"})
    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable("local") String local, @PathVariable("id") int id) {
        return categoryService.getCategoryById(id, local);
    }

    @Secured({"ROLE_GET CATEGORY", "ROLE_XEM DANH MỤC"})
    @GetMapping("/sort/{id}")
    public List<CategoryResponse> sortCategory(@PathVariable("local") String local, @PathVariable("id") int id) {
        return categoryService.sortCategory(id, local);
    }

    @Secured({"ROLE_CREATE CATEGORY", "ROLE_TẠO DANH MỤC"})
    @PostMapping
    public CategoryResponse addCategory(@PathVariable("local") String local, @RequestBody CategoryRequest categoryDTO) {
        return categoryService.addCategory(categoryDTO, local);
    }

    @Secured({"ROLE_EDIT CATEGORY", "ROLE_CHỈNH SỬA DANH MỤC"})
    @PutMapping("/{id}")
    public CategoryResponse editCategory(@PathVariable("local") String local, @PathVariable("id") int id,
                                         @RequestBody CategoryRequest categoryDTO) {
        return categoryService.editCategory(id, categoryDTO, local);
    }

    @PutMapping("/change_active/{id}")
    public CategoryResponse changeActiveCategory(@PathVariable("local") String local, @PathVariable("id") int id) {
        return categoryService.changeActiveCategory(id, local);
    }

    @Secured({"ROLE_UPDATE POSITION CATEGORY", "ROLE_CHỈNH SỬA VỊ TRÍ DANH MỤC"})
    @PutMapping("/update_position/{idParent}/{idCategory}")
    public List<CategoryResponse> updatePosition(@PathVariable("local") String local,
                                                 @PathVariable("idParent") int idParent,
                                                 @PathVariable("idCategory") int idCategory,
                                                 @RequestParam(value = "position", required = true) int position) {
        return categoryService.updatePosition(local, idParent, idCategory, position);
    }

    @Secured({"ROLE_DELETE CATEGORY", "ROLE_XÓA DANH MỤC"})
    @DeleteMapping("/{id}")
    public void deleteCate(@PathVariable("id") int id) {
        categoryService.deleteCategory(id);
    }

    @Secured({"ROLE_DELETE CATEGORY", "ROLE_XÓA DANH MỤC"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListCategory(@RequestBody List<Integer> ids) {
        return categoryService.deleteIds(ids);
    }
}
