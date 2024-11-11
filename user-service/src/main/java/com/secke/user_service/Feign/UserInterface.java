package com.secke.user_service.Feign;

import com.secke.user_service.Model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE")
public interface UserInterface {
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("api/products/user/{userId}")
    public ResponseEntity<List<Product>> getProductByUserId(@PathVariable String userId);
}
