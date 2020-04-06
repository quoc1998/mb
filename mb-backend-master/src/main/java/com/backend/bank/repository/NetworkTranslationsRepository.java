package com.backend.bank.repository;

import com.backend.bank.model.NetworkTranslations;
import com.backend.bank.model.Networks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface NetworkTranslationsRepository extends JpaRepository<NetworkTranslations, Integer> {
    Optional<NetworkTranslations> findByLocaleAndNetworks(String locale, Networks networks);

    List<NetworkTranslations> findByNetworks(Networks networks);
}
