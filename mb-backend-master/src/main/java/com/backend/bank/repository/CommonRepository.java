package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Common;

public interface CommonRepository extends JpaRepository<Common, Integer>, JpaSpecificationExecutor<Common> {
    Common findByNameAndLocale(String name, String locale);
}