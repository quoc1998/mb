package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.CustomerFontends;

public interface CustomerFontendsRepository extends JpaRepository<CustomerFontends, Integer>, JpaSpecificationExecutor<CustomerFontends> {

}