package com.backend.bank.repository;

import com.backend.bank.model.SliderSlides;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.SliderSlideTranslation;

import java.util.Optional;

public interface SliderSlideTranslationRepository extends JpaRepository<SliderSlideTranslation, Integer>, JpaSpecificationExecutor<SliderSlideTranslation> {

    Optional<SliderSlideTranslation> findBySliderSlidesAndLocale(SliderSlides sliderSlides,String local);
}