package com.backend.bank.dto.response.news;

import com.backend.bank.dto.request.CategoryNewsRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
public class NewsReponseDto {
    private int newsId;
    private String local;
    private String title;
    private String shortDescription;
    private String description;
    private String base_image;
    private  String miniImage;
    private Boolean is_sticky;
    private Boolean is_active;
    private String author_name;
    private String url;
    private String meta_title;
    private String meta_description;
    private String meta_keyword;
    private  Integer createdByUserId;
    private  Integer approvedByUserId;
    private  Integer editByUserId;
    private Calendar created_at;
    private Calendar updated_at;
    private List<CategoryNewsRequest> categories=new ArrayList<>();

    private List<NewsBlocksReponseDto> newsBlocks;
}
