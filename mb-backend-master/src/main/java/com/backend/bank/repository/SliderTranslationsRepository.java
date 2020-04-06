package com.backend.bank.repository;

import com.backend.bank.model.Sliders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.SliderTranslations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SliderTranslationsRepository extends JpaRepository<SliderTranslations, Integer>, JpaSpecificationExecutor<SliderTranslations> {
    Optional<SliderTranslations> findBySliderAndLocate(Sliders sliders,String local);
}