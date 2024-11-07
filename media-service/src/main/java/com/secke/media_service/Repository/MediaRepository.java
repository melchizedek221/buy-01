package com.secke.media_service.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.secke.media_service.Model.Media;


public interface MediaRepository extends MongoRepository<Media, String> {
    //ToDO: jsp

}