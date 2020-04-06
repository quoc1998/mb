package com.backend.bank.controller;

import com.backend.bank.converters.bases.converter.SliderConverter;
import com.backend.bank.dto.request.SliderRequestDto;
import com.backend.bank.dto.response.slider.SliderReponseDto;
import com.backend.bank.repository.SlidersRepository;
import com.backend.bank.service.SlidersService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("{local}/api/slider")
public class SliderController {
    @Autowired
    SlidersRepository slidersRepository;

    @Autowired
    SliderConverter sliderConverter;

    @Autowired
    SlidersService slidersService;

    @Secured({"ROLE_GET SLIDER", "ROLE_XEM SLIDER"})
    @GetMapping
    @JsonIgnore
    public List<SliderReponseDto> findByAll(@PathVariable("local") String local) {
        List<SliderReponseDto> sliderReponseDtos = slidersService.findAll(local);
        return sliderReponseDtos;
    }

    @Secured({"ROLE_GET SLIDER", "ROLE_XEM SLIDER"})
    @GetMapping("/{id}")
    public SliderReponseDto findById(@PathVariable("id") Integer id, @PathVariable("local") String local) {

        return slidersService.findById(id, local);
    }

    @Secured({"ROLE_CREATE SLIDER", "ROLE_TẠO SLIDER"})
    @PostMapping
    public SliderReponseDto addSlider(@RequestBody SliderRequestDto sliderRequestDto, @PathVariable("local") String local) {
        return slidersService.addSlider(sliderRequestDto, local);
    }

    @Secured({"ROLE_EDIT SLIDER", "ROLE_CHỈNH SỬA SLIDER"})
    @PutMapping("/{id}")
    public SliderReponseDto editSlider(@PathVariable("id") Integer id, @RequestBody SliderRequestDto sliderRequestDto,
                                       @PathVariable("local") String local) {

        return slidersService.editSlider(id, sliderRequestDto, local);
    }

    @Secured({"ROLE_DELETE SLIDER", "ROLE_XÓA SLIDER"})
    @DeleteMapping("/{id}")
    public void deleteSlider(@PathVariable("id") Integer id) {
        slidersService.deleteSliderById(id);
    }


    @Secured({"ROLE_DELETE SLIDER", "ROLE_XÓA SLIDER"})
    @DeleteMapping("/slideslider/{id}")
    public void deleteSlideslider(@PathVariable("id") Integer id) {
        slidersService.deleteSlidesliderById(id);
    }

    @Secured({"ROLE_DELETE SLIDER", "ROLE_XÓA SLIDER"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListSlider(@RequestBody List<Integer> ids) {
        return slidersService.deleteIds(ids);
    }

}
