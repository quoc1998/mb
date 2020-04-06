package com.backend.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.backend.bank.model.BlockTranslations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockTranslationsRepository extends JpaRepository<BlockTranslations, Integer>, JpaSpecificationExecutor<BlockTranslations> {

    //@Query("from BlockTranslations b where b.locale = ?1 and b.blocks.id = ?2")
    BlockTranslations findByLocaleAndAndBlocks_Id(String local, Integer id_block);

    List<BlockTranslations> findByLocale(String local);


}