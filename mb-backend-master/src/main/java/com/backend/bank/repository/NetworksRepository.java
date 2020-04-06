package com.backend.bank.repository;

import com.backend.bank.model.Networks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NetworksRepository extends JpaRepository<Networks, Integer> {
    List<Networks> findAllByStatus(int status);

    @Query(value = "select * from NETWORKS " +
            "join NETWORK_TRANSLATIONS on NETWORKS.ID = NETWORK_TRANSLATIONS.NETWORK_ID WHERE " +
            "(NETWORK_TRANSLATIONS.ADDRESS_NAME LIKE %:search% AND " +
            "NETWORK_TRANSLATIONS.NETWORK_CATEGORY LIKE %:networkCategory% AND " +
            "NETWORK_TRANSLATIONS.DISTRICT_CITY LIKE %:districtCity% AND " +
            "NETWORK_TRANSLATIONS.PROVINCE_CITY LIKE %:provinceCity% AND NETWORKS.STATUS LIKE %:status%)", nativeQuery = true)
    List<Networks> searchAll(@Param("search") String search,@Param("networkCategory")String networkCategory,
                             @Param("districtCity") String districtCity,@Param("provinceCity") String provinceCity,
                             @Param("status") Integer status);
}
