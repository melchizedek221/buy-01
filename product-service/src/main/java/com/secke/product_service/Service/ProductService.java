package com.secke.product_service.Service;


import com.secke.product_service.Error.BadRequestException;
import com.secke.product_service.Error.ResourceNotFoundException;
import com.secke.product_service.Model.Product;
import com.secke.product_service.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {
    @Autowired
    ProductRepository productRepo;

//    @Autowired
//    UserService userService;

    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    public Product addProduct(String userId, Product productDetails) {
        try {
//            User user = userService.getUserById(userId);
//            if (user == null || !validProduct(productDetails)) {
//                throw new BadRequestException("The product's name and/or price are incorrect");
//            }
            Product product = new Product();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setUserId(userId);
            return productRepo.save(product);

        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }
    private boolean validProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()
                || product.getPrice() == null || product.getPrice() < 0
        ){
            return false;
        }
        return true;
    }

    public Product getProductById(String id) {
        return productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found"));
    }

    public void deleteProduct(String id) {
        productRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("The product to delete is not found"));
        productRepo.deleteById(id);
    }

    public Product updateProduct(String id, Product productDetails) {
        try {

            if (!validProduct(productDetails)) {
                throw new BadRequestException("The product's name and/or price are incorrect");
            }
            Product product = getProductById(id);
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setDescription(productDetails.getDescription());
            return productRepo.save(product);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    public List<Product> getUserProducts(String userId) {
//        try {
//            return productRepo.findAll().stream().filter(product -> product.getUserId().equals(userId)).toList();
//        } catch (IllegalArgumentException e) {
//            throw new ResourceNotFoundException("Product not found");
//        }
        try {
            List<Product> products = productRepo.findAll().stream().filter(product -> product.getUserId().equals(userId)).toList();
            if (products.isEmpty()) {
                throw new ResourceNotFoundException("No products found for user: " + userId);
            }
            return products;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user products", e);
        }
    }
}
