package com.backend.bank.dto.request;

import com.backend.bank.model.PageTranslations;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PagesRequestDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private String slug;

    private int parent_id;

    private int position;

    private String baseImage;

    private String miniImage;

    private Integer is_active;

    private Integer has_sidebar;

    private Integer template;

    private String section;

    @NotNull(message = "menuMiddleId not null")
    private Integer menuMiddleId;

    private Integer sliderId;

    private  String name;

    private String meta_title;

    private String meta_keyword;

    private String meta_description;

    private List<PageBlockRequestDto> pageBlocks;

    @NotNull(message = "team not null")
    private Integer teams;
}
