package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.MailSettings;

public interface MailSettingsRepository extends JpaRepository<MailSettings, Integer>, JpaSpecificationExecutor<MailSettings> {

}