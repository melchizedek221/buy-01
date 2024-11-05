package com.secke.product_service.Repository;

import com.secke.product_service.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
//    Optional<Product> findByUserId(String userId);
}
