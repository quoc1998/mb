package com.backend.bank.repository;

import com.backend.bank.model.BlockValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockValueRepository extends JpaRepository<BlockValues, Integer> {
    List<BlockValues> findAllByBlocks_Id(Integer id);

    BlockValues findByIdAndBlocks_Id(Integer id, Integer idBlock);

    void deleteById(Integer id);
}

