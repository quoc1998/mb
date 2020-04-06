package com.backend.bank.repository;

import com.backend.bank.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Integer>, JpaSpecificationExecutor<Images> {
    List<Images> findAll();

    Optional<Images> findById(Integer id);


    List<Images> findByNameContaining(String searchIMG);


    @Transactional
    @Modifying
    @Query(value = "UPDATE images SET path_image= REPLACE(path_image, ?1, ?2), url_image = REPLACE(url_image, ?1, ?2)",
            nativeQuery = true)
    void updatePathImage(String pathOld, String pathNew);


    @Transactional
    @Modifying
    @Query(value = "delete FROM images where path_image like :path%", nativeQuery = true)
    void deletePathImage(@Param("path") String path);

    @Transactional
    @Modifying
    @Query("delete from Images i where i.id in ?1")
    void deleteAllById(List<Integer> ids);

}