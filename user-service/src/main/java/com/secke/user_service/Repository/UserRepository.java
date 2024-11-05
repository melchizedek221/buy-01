package com.secke.user_service.Repository;

import com.secke.user_service.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);
    Optional<User> findByEmail(String email);
}
