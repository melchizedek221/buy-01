package com.secke.media_service.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

import javax.imageio.ImageIO;

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
        
        if (file.getSize() > 2 * 1024 * 1024) { // 2MB limit
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceeds the 2MB limit");
        }

        // Validate file typee if not img
        try {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type. Only image files are allowed.");
            }
            
            // Optionally, you can further check the file with ImageIO
            if (ImageIO.read(file.getInputStream()) == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is not a valid image.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading the image file.");
        }

        try {
            Media media = mediaService.uploadImage(file, productId);

            // successsss
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
