package com.backend.bank.repository;

import com.backend.bank.model.SendMailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SendMailRepository extends JpaRepository<SendMailEntity, Integer> {

    Page<SendMailEntity> findAllByFormsId(Pageable pageable, Integer formId);
}
