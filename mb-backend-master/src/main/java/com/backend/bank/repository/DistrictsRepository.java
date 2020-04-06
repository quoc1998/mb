package com.backend.bank.repository;

import com.backend.bank.model.Districts;
import com.backend.bank.model.Provinces;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictsRepository extends JpaRepository<Districts, Integer> {
    @Query(
            value = "select * from districts where districts.province_id = ?1",
            nativeQuery = true)
    List<Districts> findByProvinces(int provinces);
}
