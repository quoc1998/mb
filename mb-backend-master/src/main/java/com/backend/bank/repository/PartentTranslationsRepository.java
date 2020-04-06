package com.backend.bank.repository;

import com.backend.bank.model.PartentTranslations;
import com.backend.bank.model.TargetTranslations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartentTranslationsRepository  extends JpaRepository<PartentTranslations,Integer> {
   List<PartentTranslations> findAllByParentIdAndLocale(int partentId,String locale);
}
