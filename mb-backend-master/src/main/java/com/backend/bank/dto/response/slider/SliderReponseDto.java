package com.backend.bank.dto.response.slider;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class SliderReponseDto {
    private Integer id;
    private Integer autoPlay;

    private Integer autoPlaySpeed;

    private Integer arrows;

    private Integer dots;

    private String name;

    List<SliderSlideReponseDto> sliderSlides = new ArrayList<>();
}
