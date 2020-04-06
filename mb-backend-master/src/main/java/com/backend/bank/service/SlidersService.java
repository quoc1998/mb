package com.backend.bank.service;

import com.backend.bank.dto.request.SliderRequestDto;
import com.backend.bank.dto.response.slider.SliderReponseDto;

import java.util.List;

public interface SlidersService {
    List<SliderReponseDto> findAll(String local);
    SliderReponseDto findById(Integer id,String local);
    SliderReponseDto addSlider(SliderRequestDto sliderRequestDto,String local);
    SliderReponseDto editSlider(Integer id, SliderRequestDto sliderRequestDto,String local);
    void deleteSliderById(Integer id);
    void deleteSlidesliderById(Integer id);
    Boolean deleteIds(List<Integer> ids);

}
