package com.backend.bank.controller.fontend;

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
import java.util.List;

@RestController
@RequestMapping("/api/fe/image")
public class ImageFrontendController {
    @Autowired
    ImageService imageService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping()
    public List<ImagesReponseDTO> findAll() {
        return imageService.findAll();
    }

    @GetMapping("/{idImage}")
    public ImagesReponseDTO findById(@PathVariable("idImage") Integer idImage) {
        return imageService.findById(idImage);
    }

    @GetMapping("getitems")
    public List<MediaItemReponseDTO> getItems(@RequestParam("param") String folderpath) {
        //String folderpath = request.getRequestURI().split(request.getContextPath() + "/getitems/")[1];
        if (folderpath.startsWith("/")) {
            folderpath = folderpath.substring(1, folderpath.length());
        }
        return imageService.getItems(folderpath);
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

}
