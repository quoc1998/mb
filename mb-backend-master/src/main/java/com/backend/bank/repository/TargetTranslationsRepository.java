package com.backend.bank.repository;

import com.backend.bank.model.Sliders;
import com.backend.bank.model.TargetTranslations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetTranslationsRepository extends JpaRepository<TargetTranslations,Integer> {

    List<TargetTranslations> findAllByTargetIdAndLocale(int targetId,String locale);
}
