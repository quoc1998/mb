package com.backend.bank.service;

import com.backend.bank.dto.response.ImagesReponseDTO;
import com.backend.bank.dto.response.MediaItemReponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImagesReponseDTO> findAll();

    ImagesReponseDTO findById(Integer idImage);

    List<MediaItemReponseDTO> getItems(String folderpath);

    ImagesReponseDTO addImage(MultipartFile file);

    ImagesReponseDTO addImageWithFolder(MultipartFile file, String folderName,Long size);
    List<ImagesReponseDTO> addListFileWithFolder(MultipartFile [] files, String folderName);

    Boolean addFolder(String path);

    ImagesReponseDTO editImage(Integer idImage, String name,String path);

    Boolean deleteImage(Integer idImage);

    List<ImagesReponseDTO> seachIMG(String seachIMG);

    Boolean deleteFolder(String path);

    Boolean editFolder(String folderOld, String folderNew);

    Boolean moveFolder(String pathOld, String pathNew);

    Boolean deleteIds(List<Integer> ids);

}
