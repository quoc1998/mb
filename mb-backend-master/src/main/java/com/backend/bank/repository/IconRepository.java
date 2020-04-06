package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Icon;

public interface IconRepository extends JpaRepository<Icon, Integer>, JpaSpecificationExecutor<Icon> {

}