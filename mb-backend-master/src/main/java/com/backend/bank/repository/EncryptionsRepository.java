package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Encryptions;

public interface EncryptionsRepository extends JpaRepository<Encryptions, Integer>, JpaSpecificationExecutor<Encryptions> {

}