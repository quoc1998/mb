package com.backend.bank.dto.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Calendar;

@Data
public class MenuItemsRequestDto implements Serializable {
    private String name;
    private String description ;
    private Integer parentId;
    private Integer pagesId;
    private String type;
    private String slug;
    private String slugPages;
    private String icon;
    private String url;
    private Integer targetId;
    private Integer position;
    private Boolean root;
    private Boolean fluid;
    private Boolean active;
    private Integer categoryId;
    private Integer categoryNewId;

}
