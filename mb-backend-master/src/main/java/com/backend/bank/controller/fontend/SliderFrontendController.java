package com.backend.bank.controller.fontend;

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
@RequestMapping("{local}/api/fe/slider")
public class SliderFrontendController {
    @Autowired
    SlidersRepository slidersRepository;

    @Autowired
    SliderConverter sliderConverter;

    @Autowired
    SlidersService slidersService;


    @GetMapping
    @JsonIgnore
    public List<SliderReponseDto> findByAll(@PathVariable("local") String local) {
        List<SliderReponseDto> sliderReponseDtos = slidersService.findAll(local);
        return sliderReponseDtos;
    }

    @GetMapping("/{id}")
    public SliderReponseDto findById(@PathVariable("id") Integer id, @PathVariable("local") String local) {

        return slidersService.findById(id, local);
    }

}
