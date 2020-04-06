package com.backend.bank.repository;

import com.backend.bank.model.Blocks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlocksRepository extends JpaRepository<Blocks, Integer> {
    List<Blocks> findAll();

    @Query("SELECT b FROM Blocks b")
    Page<Blocks> getAllBy(Pageable pageable);


    @Query("from Blocks b where b.id = ?1")
    Optional<Blocks> findById(Integer id);


    @Query(value = "select * FROM blocks b JOIN BLOCK_TRANSLATIONS ON b.ID = BLOCK_TRANSLATIONS.BLOCK_ID \n" +
            "and BLOCK_TRANSLATIONS.LOCALE =:locale and BLOCK_TRANSLATIONS.NAME LIKE %:name%",
            nativeQuery = true)
    Page<Blocks> getBlocksSearch(Pageable pageable, @Param("locale") String locale, @Param("name") String name);
}
