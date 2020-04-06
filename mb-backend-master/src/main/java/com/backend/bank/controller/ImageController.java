package com.backend.bank.controller;

import com.backend.bank.dto.response.ImagesReponseDTO;

import com.backend.bank.dto.response.MediaItemReponseDTO;
import com.backend.bank.service.ImageService;
import com.backend.bank.serviceImpl.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    private FileStorageService fileStorageService;

    @Secured({"ROLE_GET MEDIA", "ROLE_XEM MEDIA"})
    @GetMapping()
    public List<ImagesReponseDTO> findAll() {
        return imageService.findAll();
    }

    @Secured({"ROLE_GET MEDIA", "ROLE_XEM MEDIA"})
    @GetMapping("/{idImage}")
    public ImagesReponseDTO findById(@PathVariable("idImage") Integer idImage) {
        return imageService.findById(idImage);
    }

    @Secured({"ROLE_GET MEDIA", "ROLE_XEM MEDIA"})
    @GetMapping("getitems")
    public List<MediaItemReponseDTO> getItems(@RequestParam("param") String folderpath) {
        //String folderpath = request.getRequestURI().split(request.getContextPath() + "/getitems/")[1];
        if (folderpath.startsWith("/")) {
            folderpath = folderpath.substring(1, folderpath.length());
        }
        return imageService.getItems(folderpath);
    }

    @Secured({"ROLE_EDIT MEDIA", "ROLE_SỬA MEDIA"})
    @PutMapping("/{idImage}")
    public ImagesReponseDTO editImage(@PathVariable Integer idImage,
                                      @RequestParam("name") String name, String path) {
        return imageService.editImage(idImage, name, path);
    }

    @Secured({"ROLE_ADD MEDIA", "ROLE_THÊM MEDIA"})
    @PostMapping()
    public ImagesReponseDTO addImage(@RequestParam("file") MultipartFile file) {
        return imageService.addImage(file);
    }

    @Secured({"ROLE_ADD MEDIA", "ROLE_THÊM MEDIA"})
    @PostMapping("/addListFileWithFolder")
    public List<ImagesReponseDTO> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("folderName") String folderName) {

        return imageService.addListFileWithFolder(files, folderName);
    }

    @Secured({"ROLE_ADD MEDIA", "ROLE_THÊM MEDIA"})
    @PostMapping("/addImageWithFolder")
    public ImagesReponseDTO addImageWithFolder(@RequestParam("file") MultipartFile file,
                                               @RequestParam("folderName") String folderName, @RequestParam("size") Long size) {
        return imageService.addImageWithFolder(file, folderName, size);
    }

    @Secured({"ROLE_ADD MEDIA", "ROLE_THÊM MEDIA"})
    @PostMapping("/add-folder")
    public Boolean addFolder(@RequestParam("path") String path) {
        return imageService.addFolder(path);
    }

    @Secured({"ROLE_EDIT MEDIA", "ROLE_SỬA MEDIA"})
    @PostMapping("/edit-folder")
    public Boolean editFolder(@RequestParam("folderOld") String folderOld, @RequestParam("folderNew") String folderNew) {
        return imageService.editFolder(folderOld, folderNew);
    }

    @Secured({"ROLE_DELETE MEDIA", "ROLE_XÓA MEDIA"})
    @DeleteMapping("/delete-folder")
    public Boolean deleteFolder(@RequestParam("path") String path) {
        return imageService.deleteFolder(path);
    }

    @Secured({"ROLE_DELETE MEDIA", "ROLE_XÓA MEDIA"})
    @DeleteMapping("/{idImage}")
    public Boolean deleteImage(@PathVariable Integer idImage) {
        return imageService.deleteImage(idImage);
    }

    @PostMapping("/search")
    public List<ImagesReponseDTO> seachImage(@RequestParam("search") String search) {
        return imageService.seachIMG(search);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        //Resource resource = fileStorageService.loadFileAsResource(fileName);
        Resource resource = fileStorageService.loadFileAsResource2(fileName, "cuongnguyen/ttn");


        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {

        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/move-folder")
    public Boolean moveFolder(@RequestParam("pathOld") String pathOld, @RequestParam("pathNew") String pathNew) {
        return imageService.moveFolder(pathOld, pathNew);
    }

    @Secured({"ROLE_DELETE MEDIA", "ROLE_XÓA MEDIA"})
    @DeleteMapping("/deleteIds")
    public Boolean deleteListImage(@RequestBody List<Integer> ids) {
        return imageService.deleteIds(ids);
    }

}
