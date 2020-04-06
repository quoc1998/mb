package com.backend.bank.serviceImpl;

import com.backend.bank.common.FileStorageProperties;
import com.backend.bank.dto.request.ImagesRequestDto;
import com.backend.bank.dto.response.ImagesReponseDTO;
import com.backend.bank.dto.response.MediaItemReponseDTO;
import com.backend.bank.exception.FileStorageException;
import com.backend.bank.exception.NotFoundException;
import com.backend.bank.model.Images;
import com.backend.bank.repository.ImagesRepository;
import com.backend.bank.service.ImageService;

import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Override
    public List<ImagesReponseDTO> findAll() {
        List<ImagesReponseDTO> imagesReponseDTOS = new ArrayList<>();
        try {
            List<Images> imagesList = imagesRepository.findAll();
            for (Images images : imagesList
            ) {
                ImagesReponseDTO imagesReponseDTO = modelMapper.map(images, ImagesReponseDTO.class);
                imagesReponseDTOS.add(imagesReponseDTO);
            }
        } catch (Exception e) {

        }
        return imagesReponseDTOS;
    }

    @Override
    public List<MediaItemReponseDTO> getItems(String folderpath) {
        List<MediaItemReponseDTO> rs = fileStorageService.getItems(folderpath);
        return rs;
    }

    @Override
    public ImagesReponseDTO findById(Integer idImage) {
        ImagesReponseDTO imagesReponseDTO = modelMapper.map(imagesRepository.findById(idImage).get(), ImagesReponseDTO.class);
        return imagesReponseDTO;
    }

    @Override
    public Boolean addFolder(String path) {
        fileStorageService.addFolder(path);
        Images images = new Images();
        images.setPath(path);
        images.setType("folder");
        imagesRepository.save(images);
        return true;
    }

    @Override
    public Boolean deleteFolder(String path) {
        try {
            fileStorageService.deleteFolder(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imagesRepository.deletePathImage(path);
        return true;
    }

    @Override
    public Boolean editFolder(String folderOld, String folderNew) {
        fileStorageService.editFolder(folderOld, folderNew);
        imagesRepository.updatePathImage(folderOld, folderNew);
        return true;

    }

    @Override
    public Boolean moveFolder(String pathOld, String pathNew) {
        Path targetLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();
        File newDirectory = new File(targetLocation + File.separator + pathNew);
        File directoryOld = new File(targetLocation + File.separator + pathOld);
        if (newDirectory.exists()) {
            try {
                if (newDirectory.delete()) {
                    System.out.println("Directory is delete!");
                } else {
                    System.out.println("Failed to delete directory");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            FileUtils.moveDirectory(directoryOld, newDirectory);
            imagesRepository.updatePathImage(pathOld, pathNew);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public Boolean deleteIds(List<Integer> ids) {
        Boolean a;
        try {
            imagesRepository.deleteAllById(ids);
            a = true;
        } catch (Exception e) {
            a = false;
        }
        return a;
    }

    @Override
    public ImagesReponseDTO addImage(MultipartFile file) {
        ImagesReponseDTO imagesReponseDTO;
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/downloadFile/")
                .path(fileName)
                .toUriString();


        try {
            ImagesRequestDto imagesRequestDto = new ImagesRequestDto();
            imagesRequestDto.setName(fileName);
            imagesRequestDto.setUrl(fileDownloadUri);
            imagesRequestDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            Images images = modelMapper.map(imagesRequestDto, Images.class);
            images.setType("file");
            imagesRepository.save(images);
            imagesReponseDTO = modelMapper.map(images, ImagesReponseDTO.class);
        } catch (Exception e) {
            imagesReponseDTO = null;
        }
        return imagesReponseDTO;
    }

    @Override
    public ImagesReponseDTO addImageWithFolder(MultipartFile file, String folderName, Long size) {
        Path targetLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        targetLocation = Paths.get(targetLocation + File.separator + folderName).toAbsolutePath().normalize();
        File newDirectory = new File(targetLocation + File.separator);
        try {
            if (!newDirectory.exists()) {
                newDirectory.mkdirs();
                System.out.println("tạo mới");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String fileName = fileStorageService.storeFileWithPath(file, targetLocation);
        ImagesReponseDTO imagesReponseDTO;
        //targetLocation = Paths.get(targetLocation + File.separator + folderName).toAbsolutePath().normalize();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("uploads/")
                .path(folderName)
                .path(fileName)
                .toUriString();
        try {
            ImagesRequestDto imagesRequestDto = new ImagesRequestDto();
            imagesRequestDto.setUrl(fileDownloadUri);
            imagesRequestDto.setSize(size);
            imagesRequestDto.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            imagesRequestDto.setPath(folderName);
            Images images = modelMapper.map(imagesRequestDto, Images.class);
            images.setType("file");
            images.setName(fileName);
            imagesRepository.save(images);
            imagesReponseDTO = modelMapper.map(images, ImagesReponseDTO.class);
        } catch (Exception e) {
            e.printStackTrace();
            imagesReponseDTO = null;
        }
        return imagesReponseDTO;
    }

    @Override
    public List<ImagesReponseDTO> addListFileWithFolder(MultipartFile files[], String folderName) {
        List<ImagesReponseDTO> imagesReponseDTOS = new ArrayList<>();
        for (MultipartFile file : files) {
            ImagesReponseDTO imagesReponseDTO = addImageWithFolder(file, folderName, file.getSize());
            imagesReponseDTOS.add(imagesReponseDTO);
        }

        return imagesReponseDTOS;
    }

    @Override
    public ImagesReponseDTO editImage(Integer idImage, String name, String path) {
        Path targetLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        ImagesReponseDTO imagesReponseDTO;
        //File newDirectory = new File(targetLocation + File.separator + folderName);
        Images images = imagesRepository.findById(idImage).get();
        if (path.isEmpty()) {
            try {


                File newDirectory = new File(targetLocation + File.separator + images.getPath() + name);
                File directoryOld = new File(targetLocation + File.separator + images.getPath() + images.getName());
                try {
                    if (!newDirectory.exists()) {
                        directoryOld.renameTo(newDirectory);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NotFoundException("Not found image");
                }

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("uploads/")
                        .path(images.getPath())
                        .path(name)
                        .toUriString();
                images.setUrl(fileDownloadUri);
                images.setName(name);
                images.setUpdatedAt(new Date());
                imagesRepository.save(images);
                imagesReponseDTO = modelMapper.map(images, ImagesReponseDTO.class);
            } catch (Exception e) {
                imagesReponseDTO = null;
            }
        } else {
            try {
                File oldFile = new File(targetLocation + File.separator + images.getPath() + images.getName());
                File newFile = new File(targetLocation + File.separator + path + name);
                try {
                    FileUtils.moveFile(oldFile, newFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new NotFoundException("Not found image");
                }

                String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("uploads/")
                        .path(path)
                        .path(name)
                        .toUriString();
                images.setUrl(fileDownloadUri);
                images.setName(name);
                images.setPath(path);
                images.setUpdatedAt(new Date());
                imagesRepository.save(images);
                imagesReponseDTO = modelMapper.map(images, ImagesReponseDTO.class);
            } catch (Exception e) {
                imagesReponseDTO = null;
            }
        }

        return imagesReponseDTO;
    }

    @Override
    public Boolean deleteImage(Integer idImages) {
        Boolean aBoolean;
        Path targetLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Images images = imagesRepository.findById(idImages).get();
            if (images == null){
                throw new NotFoundException("Not Found Image");
            }
            imagesRepository.deleteById(idImages);
            targetLocation = Paths.get(targetLocation + File.separator + images.getPath()).toAbsolutePath().normalize();
            File image = new File(targetLocation + File.separator + images.getName());
            try {
                if (image.exists()) {
                    image.delete();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            aBoolean = true;
        } catch (Exception e) {
            e.printStackTrace();
            aBoolean = false;
        }
        return aBoolean;
    }

    @Override
    public List<ImagesReponseDTO> seachIMG(String seachIMG) {
        List<Images> imagesList = imagesRepository.findByNameContaining(seachIMG);
        List<ImagesReponseDTO> imagesReponseDTOS = new ArrayList<>();
        for (Images images : imagesList
        ) {
            ImagesReponseDTO imagesReponseDTO = modelMapper.map(images, ImagesReponseDTO.class);
            imagesReponseDTOS.add(imagesReponseDTO);
        }
        return imagesReponseDTOS;
    }

    private static void move(File sourceFile, File destFile) {
        if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles();
            assert files != null;
            for (File file : files) move(file, new File(destFile, file.getName()));
            if (!sourceFile.delete()) throw new RuntimeException();
        } else {
            if (!destFile.getParentFile().exists())
                if (!destFile.getParentFile().mkdirs()) throw new RuntimeException();
            if (!sourceFile.renameTo(destFile)) throw new RuntimeException();
        }
    }
}
