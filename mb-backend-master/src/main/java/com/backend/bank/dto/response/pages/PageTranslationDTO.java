package com.backend.bank.dto.response.pages;

import com.backend.bank.model.PageTranslations;
import lombok.Data;

@Data
public class PageTranslationDTO {
    private int id;
    private String locale;
    private String meta_title;
    private String meta_keyword;
    private String meta_description;
    private  String name;
    private int pageId;

    public PageTranslationDTO() {
    }
    public PageTranslationDTO(PageTranslations pageTranslations) {
        super();
        this.id =pageTranslations.getId();
        this.locale = pageTranslations.getLocale();
        this.meta_description =pageTranslations.getMeta_description();
        this.meta_keyword = pageTranslations.getMeta_keyword();
        this.meta_title = pageTranslations.getMeta_title();
        this.pageId = pageTranslations.getPages().getId();
    }
}
