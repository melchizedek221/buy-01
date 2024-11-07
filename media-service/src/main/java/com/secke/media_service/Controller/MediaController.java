package com.secke.media_service.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

import com.secke.media_service.Model.Media;
import com.secke.media_service.Repository.MediaRepository;
import com.secke.media_service.Service.MediaService;


@RestController
@RequestMapping("/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaRepository mediaRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam("productId") String productId) {
        try {
            Media media = mediaService.uploadImage(file, productId);

            // Message de succès
            Map<String, String> response = new HashMap<>();
            response.put("message", "Upload successful");
            response.put("mediaId", media.getId());

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

}
