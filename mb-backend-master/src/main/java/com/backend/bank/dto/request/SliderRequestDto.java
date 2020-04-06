package com.backend.bank.dto.request;

import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Data
public class SliderRequestDto {

    private Integer autoPlay;

    private Integer autoPlaySpeed;

    private Integer arrows;

    private Integer dots;

    private String name;

    List<SliderSlideRequestDto> sliderSlides = new ArrayList<>();
}
