package com.backend.bank.repository;

import com.backend.bank.model.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    @Query(value = "select * from EXCHANGE_RATE e where to_char(e.DATE_UPDATE,'YYYY-MM-DD')= ?1  ORDER BY e.CREATED_AT DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY",
            nativeQuery = true)
    ExchangeRate findByDate(String date);

    @Query(value = "select * from EXCHANGE_RATE e ORDER BY e.CREATED_AT DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY",
            nativeQuery = true)
    ExchangeRate findByDateNew();

    @Query(value = "select * from EXCHANGE_RATE e where to_char(e.DATE_UPDATE,'YYYY/MM/DD') >= ?1 and to_char(e.DATE_UPDATE,'YYYY/MM/DD') <= ?2",
            nativeQuery = true)
    Page<ExchangeRate> findRangeDate(Pageable pageable, String dateStart, String dateStop);

    @Query("SELECT b FROM ExchangeRate b")
    Page<ExchangeRate> getAllBy(Pageable pageable);
}
