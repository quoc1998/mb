package com.backend.bank.dto.response.pages;

import com.backend.bank.dto.response.menu.MenuItemsReponseDto;
import com.backend.bank.dto.response.menu.MenuMiddle;
import com.backend.bank.dto.response.slider.SliderReponseDto;
import com.backend.bank.model.Sliders;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PagesResponseDto implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private Integer id;

    private int parent_id;

    private String slug;
    private String locale;

    private Integer is_active;

    private MenuMiddle menuMiddle;

    private SliderReponseDto sliders;

    private Integer has_sidebar;

    private Integer template;

    private String section;

    private int position;

    private  String name;

    private String baseImage;

    private String miniImage;

    private String meta_title;

    private String meta_keyword;
    private Integer team;

    private String meta_description;

    private List<PageBlockReponseDto> pageBlocks;

    private List<MenuItemsReponseDto> menuItems;

   }
