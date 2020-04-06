package com.backend.bank.dto.response.menu;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class MenuItemsReponseDto implements Serializable {
    private Integer id;
    private String name;
    private String description ;
    private Integer menuId;
    private Integer categoryId;
    private Integer pageId;
    private Integer parentId;
    private String type;
    private String icon;
    private String url;
    private String slug;
    private String slugPages;
    private Integer target;
    private Integer position;
    private Boolean root;
    private Boolean fluid;
    private Boolean active;
    private Integer categoryNewId;
}
