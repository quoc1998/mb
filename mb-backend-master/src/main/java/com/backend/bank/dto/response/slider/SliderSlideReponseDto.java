package com.backend.bank.dto.response.slider;

import com.backend.bank.dto.response.IconReponseDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class SliderSlideReponseDto {
    private Integer id;

    private Integer sliderId;

    private String options;

    private String callToActionUrl;

    private String urlVideoYoutobe;

    private Integer position;

    private String image;

    private String caption1;

    private String caption2;

    private String caption3;

    private String callToActionText;
}
