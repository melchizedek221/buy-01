package com.secke.media_service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.secke.media_service.Model.Media;
import com.secke.media_service.Repository.MediaRepository;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    public Media uploadImage(MultipartFile file, String productId) throws IOException {
        // Définir le chemin
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String filePath = uploadDir + file.getOriginalFilename();

        System.out.println("Saving file to: " + filePath);

        File destinationFile = new File(filePath);
        file.transferTo(destinationFile);

        // Sauvegarder dans MongoDB
        Media media = new Media();
        media.setImagePath(filePath);
        media.setProductId(productId);

        return mediaRepository.save(media);
    }
}

