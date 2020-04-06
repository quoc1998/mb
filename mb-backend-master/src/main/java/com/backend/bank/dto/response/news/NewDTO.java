package com.backend.bank.dto.response.news;

import com.backend.bank.dto.request.CategoryNewsRequest;
import com.backend.bank.dto.request.NewsBlocksRequestDto;
import com.backend.bank.model.News;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
public class NewDTO {

    private int id;

    @NotNull(message = "title not null")
    private String title;

    private String shortDescription;

    private String description;

    @NotNull(message = "base_image not null")
    private String base_image;

    private  String miniImage;

    @NotNull(message = "is_sticky not null")
    private Boolean is_sticky;

    @NotNull(message = "is_active not null")
    private Boolean is_active;

    private String author_name;

    @NotNull(message = "url not null")
    private String url;

    @NotNull(message = "meta_title not null")
    private String meta_title;

    private String meta_description;

    private String meta_keyword;

    private Calendar created_at;
    private Calendar updated_at;

    private List<CategoryNewsRequest> categories=new ArrayList<>();

    private List<NewsBlocksRequestDto> newsBlocks;

    public NewDTO() {
    }
}
