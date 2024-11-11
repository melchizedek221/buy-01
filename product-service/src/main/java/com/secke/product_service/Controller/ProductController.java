package com.secke.product_service.Controller;


import com.secke.product_service.Model.Product;
import com.secke.product_service.Service.ProductService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    ProductService productService;
    private Bucket bucket;

    public ProductController() {
        Bandwidth limit = Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(productService.getAllProducts());
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestParam String userId, @RequestBody Product productDetail) {
        if (bucket.tryConsume(1)) {
//            String userId = "";
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.isAuthenticated()) {
//                Object principal = authentication.getPrincipal();
//
//                if (principal instanceof UserDetails) {
//                    UserPrincipal userPrincipal = (UserPrincipal) principal;
//                    userId = userPrincipal.getId();
//                }
//            }
            return ResponseEntity.ok(productService.addProduct(userId, productDetail));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }
    @PreAuthorize("hasAuthority('SELLER') and authentication.principal.getId() == @productService.getProductById(#id)?.userId")
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @Valid @RequestBody Product productDetail) {
        if (bucket.tryConsume(1)){
            return ResponseEntity.ok(productService.updateProduct(id, productDetail));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    @PreAuthorize("hasAuthority('SELLER') or authentication.principal.getId() == @productService.getProductById(#id)?.userId")
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @PreAuthorize("hasAuthority('SELLER') and authentication.principal.getId() == @productService.getProductById(#id)?.userId")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(productService.getProductById(id));
        }
        return  ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }

    // This method allow a specific user to retrieve his created products
//    @PreAuthorize("hasAuthority('SELLER') and authentication.principal.getId() == #userId")
    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Product>> getProductByUserId(@PathVariable String userId) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(productService.getUserProducts(userId));
        }
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
    }





}
