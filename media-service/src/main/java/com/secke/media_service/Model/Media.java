package com.secke.media_service.Model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document (collection = "media") 
@Getter @Setter
public class Media {
    @Id
    private String id;

    private String imagePath;

    private String productId;

}
