package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Countries;

public interface CountriesRepository extends JpaRepository<Countries, Integer>, JpaSpecificationExecutor<Countries> {

}