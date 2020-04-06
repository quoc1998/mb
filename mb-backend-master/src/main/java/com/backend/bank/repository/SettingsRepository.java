package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Void>, JpaSpecificationExecutor<Settings> {

}