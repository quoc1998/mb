package com.backend.bank.serviceImpl;


import com.backend.bank.common.FileStorageProperties;
import com.backend.bank.dto.response.MediaItemReponseDTO;
import com.backend.bank.exception.FileStorageException;
import com.backend.bank.exception.MyFileNotFoundException;
import com.backend.bank.model.Images;
import com.backend.bank.repository.ImagesRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String addFolder(String path) {
        try {
            Path targetLocation = this.fileStorageLocation;
            File newDirectory = new File(targetLocation + File.separator + path);
            if (!newDirectory.exists()) {
                if (newDirectory.mkdirs()) {
                    return "Directory is created!";
                } else {
                    return "Failed to create directory!";
                }
            }
            return "Directory is created!";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean editFolder(String folderOld, String folderNew) {
        Path targetLocation = Paths.get("uploads")
                .toAbsolutePath().normalize();
        File newDirectory = new File(targetLocation + File.separator + folderNew);
        File directoryOld = new File(targetLocation + File.separator + folderOld);
        try {
            if (!newDirectory.exists()) {
                directoryOld.renameTo(newDirectory);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public String deleteFolder(String path) throws IOException {
        Path targetLocation = this.fileStorageLocation;
        File newDirectory = new File(targetLocation + File.separator + path);
        if (newDirectory.exists()) {
            FileUtils.deleteDirectory(newDirectory);
        }
        return "Directory is delete ";
    }

    public List<MediaItemReponseDTO> getListItem(Path targetLocation, Boolean isFile) {
        List<MediaItemReponseDTO> rs = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(targetLocation, 1)) {
            List<String> files = new ArrayList<>();
            if (isFile) {
                files = walk.filter(Files::isRegularFile)
                        .map(x -> x.getFileName().toString()).collect(Collectors.toList());
            } else {
                files = walk.filter(Files::isDirectory)
                        .map(x -> x.getFileName().toString()).collect(Collectors.toList());
            }
            for (String item : files) {
                String path = "";
                if (isFile) {
                    path = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/api/image/downloadFile/")
                            .path(item)
                            .toUriString();
                } else {
                    path = item;
                }
                MediaItemReponseDTO dto = new MediaItemReponseDTO(isFile, path);
                rs.add(dto);
            }
            return rs;

        } catch (IOException e) {
            return rs;
        }
    }

    public List<MediaItemReponseDTO> getItems(String folderpath) {
        List<MediaItemReponseDTO> rs = new ArrayList<>();
        Path targetLocation = this.fileStorageLocation.resolve(folderpath);
        if (!Files.exists(targetLocation)) {
            return new ArrayList<>();
        }
        rs.addAll(getListItem(targetLocation, true));
        rs.addAll(getListItem(targetLocation, false));
        return rs;
    }


    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeFileWithPath(MultipartFile file, Path path) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            List<String> fileNameTempSlip = Arrays.asList(fileName.split("\\."));
            List<Images> imagesList = imagesRepository.findByNameContaining(fileNameTempSlip.get(0));
            if (imagesList.size() != 0){
                StringBuilder nameMedia = new StringBuilder();
                nameMedia.append(fileNameTempSlip.get(0)).append("-")
                        .append(imagesList.size()).append(".")
                        .append(fileNameTempSlip.get(1));
                fileName = nameMedia.toString();
            }
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            //Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Path targetLocation = path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public Resource loadFileAsResource2(String fileName, String path) {
        try {
            Path filePath = this.fileStorageLocation.resolve(path + "/" + fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public Path getPath(String path) {
        return this.fileStorageLocation.resolve(path).normalize();
    }
}
