package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Generals;

public interface GeneralsRepository extends JpaRepository<Generals, Integer>, JpaSpecificationExecutor<Generals> {

}