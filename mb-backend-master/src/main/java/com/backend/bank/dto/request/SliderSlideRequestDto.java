package com.backend.bank.dto.request;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
public class SliderSlideRequestDto {

    private Integer id;

    private String options;

    private String callToActionUrl;

    private String urlVideoYoutube;

    private Integer position;

    private String caption1;

    private String caption2;

    private String caption3;

    private String callToActionText;

    private String image;
}
