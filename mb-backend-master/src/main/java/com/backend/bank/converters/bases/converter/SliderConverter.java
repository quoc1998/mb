package com.backend.bank.converters.bases.converter;

import com.backend.bank.dto.request.*;
import com.backend.bank.dto.response.*;
import com.backend.bank.dto.response.slider.SliderReponseDto;
import com.backend.bank.dto.response.slider.SliderSlideReponseDto;
import com.backend.bank.dto.response.slider.SliderSlideTranslationReponseDto;
import com.backend.bank.dto.response.slider.SliderTranslationReponse;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.*;
import com.backend.bank.repository.SliderSlideTranslationRepository;
import com.backend.bank.repository.SliderSlidesRepository;
import com.backend.bank.repository.SliderTranslationsRepository;
import com.backend.bank.repository.SlidersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class SliderConverter {

    @Autowired
    SliderSlidesRepository sliderSlidesRepository;

    @Autowired
    SlidersRepository slidersRepository;
    @Autowired
    SliderTranslationsRepository sliderTranslationsRepository;
    @Autowired
    SliderSlideTranslationRepository sliderSlideTranslationRepository;
    Calendar calendar = Calendar.getInstance();

    public SliderReponseDto SliderConverter(Sliders sliders, String local) {
        SliderReponseDto sliderReponseDto = new SliderReponseDto();
        sliderReponseDto.setArrows(sliders.getArrows());
        sliderReponseDto.setAutoPlay(sliders.getAutoPlay());
        sliderReponseDto.setAutoPlaySpeed(sliders.getAutoPlaySpeed());
        sliderReponseDto.setDots(sliders.getDots());
        sliderReponseDto.setId(sliders.getId());
        List<SliderSlideReponseDto> sliderSlideReponseDtos = new ArrayList<>();
        for (SliderSlides sliderSlides : sliders.getSliderSlides()
        ) {
            SliderSlideReponseDto sliderSlideReponseDto = this.sliderSlideConverter(sliderSlides, local);
            sliderSlideReponseDtos.add(sliderSlideReponseDto);

        }
        SliderTranslations sliderTranslations = sliderTranslationsRepository.findBySliderAndLocate(sliders, local).orElse(null);
        if (sliderTranslations != null) {
            sliderReponseDto.setName(sliderTranslations.getName());
        } else {
            sliderTranslations = new SliderTranslations();
            sliderTranslations.setName(sliders.getSliderTranslations().get(0).getName());
            sliderTranslations.setLocate(local);
            sliderTranslations.setSlider(sliders);
            sliderTranslations.setName("");
            sliderTranslationsRepository.save(sliderTranslations);
            sliderReponseDto.setName(sliderTranslations.getName());
        }

        sliderReponseDto.setSliderSlides(sliderSlideReponseDtos);
        return sliderReponseDto;
    }

    public List<SliderReponseDto> sliderReponseDtosConverter(List<Sliders> sliders, String local) {
        List<SliderReponseDto> sliderReponseDtos = new ArrayList<>();

        for (Sliders slider : sliders
        ) {
            SliderReponseDto sliderReponseDto = this.SliderConverter(slider, local);

            sliderReponseDtos.add(sliderReponseDto);
        }


        return sliderReponseDtos;
    }

    ;

    public SliderSlideTranslation sliderSlideTranslationConverterEntity(SliderSlides sliderSlides, SliderSlideRequestDto sliderSlideRequestDto, String local) {
        SliderSlideTranslation sliderSlideTranslation = new SliderSlideTranslation();
        sliderSlideTranslation.setCallToActionText(sliderSlideRequestDto.getCallToActionText());
        sliderSlideTranslation.setCaption1(sliderSlideRequestDto.getCaption1());
        sliderSlideTranslation.setCaption2(sliderSlideRequestDto.getCaption2());
        sliderSlideTranslation.setCaption3(sliderSlideRequestDto.getCaption3());
        sliderSlideTranslation.setImage(sliderSlideRequestDto.getImage());
        sliderSlideTranslation.setLocale(local);
        sliderSlideTranslation.setSliderSlides(sliderSlides);
        return sliderSlideTranslation;
    }

    public SliderSlideReponseDto sliderSlideConverter(SliderSlides sliderSlides, String local) {
        SliderSlideReponseDto sliderSlideReponseDto = new SliderSlideReponseDto();
        sliderSlideReponseDto.setCallToActionUrl(sliderSlides.getCallToActionUrl());
        sliderSlideReponseDto.setUrlVideoYoutobe(sliderSlides.getUrlVideoYoutobe());
        sliderSlideReponseDto.setOptions(sliderSlides.getOptions());
        sliderSlideReponseDto.setId(sliderSlides.getId());
        sliderSlideReponseDto.setPosition(sliderSlides.getPosition());
        sliderSlideReponseDto.setSliderId(sliderSlides.getSlider().getId());
        SliderSlideTranslation sliderSlideTranslation = sliderSlideTranslationRepository.findBySliderSlidesAndLocale(sliderSlides, local).orElse(null);
        if (sliderSlideTranslation == null) {
            sliderSlideTranslation = new SliderSlideTranslation();
            sliderSlideTranslation.setCallToActionText("");
            sliderSlideTranslation.setCaption1("");
            sliderSlideTranslation.setCaption2("");
            sliderSlideTranslation.setCaption3("");
            sliderSlideTranslation.setImage("");
            sliderSlideTranslation.setLocale(local);
            sliderSlideTranslation.setSliderSlides(sliderSlides);
            sliderSlideTranslationRepository.save(sliderSlideTranslation);
        }
        sliderSlideReponseDto.setImage(sliderSlideTranslation.getImage());
        sliderSlideReponseDto.setCallToActionText(sliderSlideTranslation.getCallToActionText());
        sliderSlideReponseDto.setCaption1(sliderSlideTranslation.getCaption1());
        sliderSlideReponseDto.setCaption2(sliderSlideTranslation.getCaption2());
        sliderSlideReponseDto.setCaption3(sliderSlideTranslation.getCaption3());
        return sliderSlideReponseDto;
    }

    public SliderSlides sliderSlideConverterentity(Sliders sliders, SliderSlideRequestDto sliderSlideRequestDto, String local) {
        SliderSlides sliderSlides = new SliderSlides();
        sliderSlides.setOptions(sliderSlideRequestDto.getOptions());
        sliderSlides.setCallToActionUrl(sliderSlideRequestDto.getCallToActionUrl());
        sliderSlides.setPosition(sliderSlideRequestDto.getPosition());
        sliderSlides.setUrlVideoYoutobe(sliderSlideRequestDto.getUrlVideoYoutube());
        sliderSlides.setCreatedAt(calendar.getTime());
        sliderSlides.setSlider(sliders);
        List<SliderSlideTranslation> sliderSlideTranslations = new ArrayList<>();
        SliderSlideTranslation sliderSlideTranslation = this.sliderSlideTranslationConverterEntity(sliderSlides, sliderSlideRequestDto, local);
        sliderSlideTranslations.add(sliderSlideTranslation);
        sliderSlides.setSliderSlideTranslations(sliderSlideTranslations);
        return sliderSlides;
    }


    public SliderTranslations sliderTranslationConverterEntity(Sliders sliders, SliderRequestDto sliderRequestDto, String local) {
        SliderTranslations sliderTranslations = new SliderTranslations();
        sliderTranslations.setSlider(sliders);
        sliderTranslations.setLocate(local);
        sliderTranslations.setName(sliderRequestDto.getName());
        return sliderTranslations;
    }

    public Sliders SliderConverterEntity(SliderRequestDto sliderRequestDto, String local) {
        Sliders slider = new Sliders();
        slider.setArrows(sliderRequestDto.getArrows());
        slider.setAutoPlay(sliderRequestDto.getAutoPlay());
        slider.setAutoPlaySpeed(sliderRequestDto.getAutoPlaySpeed());
        slider.setDots(sliderRequestDto.getDots());
        slider.setCreatedAt(calendar.getTime());
        List<SliderSlides> sliderSlides = new ArrayList<>();
        for (SliderSlideRequestDto sliderSlideRequestDto : sliderRequestDto.getSliderSlides()) {
            SliderSlides sliderSlide = this.sliderSlideConverterentity(slider, sliderSlideRequestDto, local);
            sliderSlides.add(sliderSlide);
        }
        slider.setSliderSlides(sliderSlides);
        List<SliderTranslations> sliderTranslationsList = new ArrayList<>();
        SliderTranslations sliderTranslations = this.sliderTranslationConverterEntity(slider, sliderRequestDto, local);
        sliderTranslationsList.add(sliderTranslations);
        slider.setSliderTranslations(sliderTranslationsList);

        return slider;
    }

    public Sliders SliderSetEntity(Sliders slider, SliderRequestDto sliderRequestDto, String local) {
        slider.setArrows(sliderRequestDto.getArrows());
        slider.setAutoPlay(sliderRequestDto.getAutoPlay());
        slider.setAutoPlaySpeed(sliderRequestDto.getAutoPlaySpeed());
        slider.setDots(sliderRequestDto.getDots());
        slider.setUpdatedAt(calendar.getTime());
        List<SliderTranslations> sliderTranslationslist = new ArrayList<>();
        SliderTranslations sliderTranslation = sliderTranslationsRepository.findBySliderAndLocate(slider, local).get();
        sliderTranslation.setName(sliderRequestDto.getName());
        sliderTranslation.setSlider(slider);
        sliderTranslationslist.add(sliderTranslation);
        slider.setSliderTranslations(sliderTranslationslist);
        List<SliderSlideRequestDto> sliderSlideRequestDtos = sliderRequestDto.getSliderSlides();
        for (SliderSlideRequestDto sliderSlideRequestDto : sliderSlideRequestDtos) {
            if (sliderSlideRequestDto.getId() == 0) {
                SliderSlides sliderSlides = this.sliderSlideConverterentity(slider, sliderSlideRequestDto, local);
                sliderSlidesRepository.save(sliderSlides);
            } else {
                Integer id = checkSliderSlider(sliderSlideRequestDto.getId(), slider.getSliderSlides());
                if (id == -1) {
                    throw new NotFoundException("Not Found SlidersSlider");
                } else {
                    slider.getSliderSlides().get(id).setUrlVideoYoutobe(sliderSlideRequestDto.getUrlVideoYoutube());
                    slider.getSliderSlides().get(id).setPosition(sliderSlideRequestDto.getPosition());
                    slider.getSliderSlides().get(id).setCallToActionUrl(sliderSlideRequestDto.getCallToActionUrl());
                    slider.getSliderSlides().get(id).setOptions(sliderSlideRequestDto.getOptions());
                    slider.getSliderSlides().get(id).setUpdatedAt(calendar.getTime());
                    Integer idTranslation = checkSliderSlideTranslation(slider.getId(), local, slider.getSliderSlides().get(id).getSliderSlideTranslations());
                    slider.getSliderSlides().get(id).getSliderSlideTranslations().get(idTranslation).setImage(sliderSlideRequestDto.getImage());
                    slider.getSliderSlides().get(id).getSliderSlideTranslations().get(idTranslation).setCallToActionText(sliderSlideRequestDto.getCallToActionText());
                    slider.getSliderSlides().get(id).getSliderSlideTranslations().get(idTranslation).setCaption1(sliderSlideRequestDto.getCaption1());
                    slider.getSliderSlides().get(id).getSliderSlideTranslations().get(idTranslation).setCaption2(sliderSlideRequestDto.getCaption2());
                    slider.getSliderSlides().get(id).getSliderSlideTranslations().get(idTranslation).setCaption3(sliderSlideRequestDto.getCaption3());
                }
            }
        }
        return slider;
    }

    public Integer checkSliderSlider(Integer id, List<SliderSlides> sliderSlideRequestDtos) {
        Integer count = sliderSlideRequestDtos.size();
        for (Integer i = 0; i < count; i++) {
            if (id.equals(sliderSlideRequestDtos.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }

    public Integer checkSliderSlideTranslation(Integer id, String local, List<SliderSlideTranslation> sliderSlideTranslations) {
        Integer count = sliderSlideTranslations.size();
        if (count.equals(0)) {
            throw new NotFoundException("Slider not found translation: " + id);
        }
        for (Integer i = 0; i < count; i++) {
            if (local.equals(sliderSlideTranslations.get(i).getLocale())) {
                return i;
            }
        }
        return -1;
    }
}
