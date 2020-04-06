package com.backend.bank.dto;

import com.backend.bank.model.CategoryTranslations;
import lombok.Data;

@Data
public class CategoryTranslationDTO {
    private int id;
    private String name;
    private String description;
    private String local;
    private int categoryId;

    public CategoryTranslationDTO(CategoryTranslations categoryTranslations) {
        this.id = categoryTranslations.getId();
        this.name = categoryTranslations.getName();
        this.description = categoryTranslations.getDescription();
        this.local = categoryTranslations.getLocale();
        this.categoryId = categoryTranslations.getCategory().getId();
    }
}
