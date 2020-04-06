package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.SliderSlides;

public interface SliderSlidesRepository extends JpaRepository<SliderSlides, Integer>, JpaSpecificationExecutor<SliderSlides> {

}