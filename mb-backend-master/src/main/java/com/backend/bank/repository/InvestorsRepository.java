package com.backend.bank.repository;

import com.backend.bank.model.Investors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvestorsRepository extends JpaRepository<Investors, Integer> {
    Optional<Investors> findById(Integer id);

    List<Investors> findAllByIsActive(Boolean aBoolean);


    @Query(value = "select * from investors i where i.type_investors_id = ?1 and to_char(created_at,'YYYY')= ?2 and is_active = ?3",
            nativeQuery = true)
    Page<Investors> findAllByTypeIsActive(Pageable pageable, Integer type,Integer year, Boolean aBoolean);

    @Query(value = "select * from investors i where i.type_investors_id = ?1 and to_char(created_at,'YYYY')= ?2 and is_active = ?3",
            nativeQuery = true)
    List<Investors> findAllByTypeIsActive(Integer type,Integer year, Boolean aBoolean);

    @Query(value = "select * from investors i where i.type_investors_id = ?1  and is_active = ?2", nativeQuery = true)
    Page<Investors> findAllByInvestorsId(Pageable pageable, Integer typeInvestorsId, Boolean aBoolean);

    @Query(value = "select * from investors i where i.type_investors_id =:typeInvestorsId " +
            "and i.detail_type_investors_id like %:detailTypeId%  and is_active =:aBoolean", nativeQuery = true)
    List<Investors> findAllByInvestorsId(@Param("typeInvestorsId") Integer typeInvestorsId,@Param("detailTypeId")
            String detailTypeId,@Param("aBoolean") Boolean aBoolean);

    @Query(value = "select * from investors i where i.type_investors_id =:typeInvestorsId and is_active =:aBoolean", nativeQuery = true)
    List<Investors> findAllByInvestorsId(@Param("typeInvestorsId") Integer typeInvestorsId,@Param("aBoolean") Boolean aBoolean);


    @Query(value = "select * from investors i where to_char(created_at,'YYYY')= ?1 and is_active = ?2", nativeQuery = true)
    List<Investors> findAllByYearAndIsActive(Integer year, Boolean aBoolean);

    @Query(value = "select * from investors i where to_char(created_at,'YYYY')= ?1 and is_active = ?2 " +
            "and i.url_video  is not null ORDER BY i.CREATED_AT DESC OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY", nativeQuery = true)
    Investors findAllByYearAndIsActiveAndSort(Integer year, Boolean aBoolean);

    @Query(value = "select * from investors i where i.type_investors_id = ?1 and to_char(created_at,'YYYY')= ?2 and is_active = ?3", nativeQuery = true)
    List<Investors> findAllByYearAndIsActiveAndTypeInvestorsId(Integer typeInvestorsId, Integer year, Integer aBoolean);

    @Query(value = "select DISTINCT to_char(created_at, 'YYYY') from investors " +
            "ORDER BY to_char(created_at, 'YYYY') DESC OFFSET ?1 ROWS FETCH NEXT ?2 ROWS ONLY ", nativeQuery = true)
    List<Integer> getAllYear(Integer page, Integer number);



}
