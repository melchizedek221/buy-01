package com.secke.product_service.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Field
    @NotNull(message = "product name is require")
    @Size(min = 2, max = 20, message = "The name must be between 2 and 20 characters")
    private String name;

    @Field
    private String description;

    @Field
    @NotNull(message = "price is require")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater than or equal to 0")
    private Double price;

    @Field
//    @NotNull(message = "the user id is require")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
//    @ForeignKey()
//    private String userId;
//    @DocumentReference(lookup = "{ '_id' : ?#{#target} }", collection = "users")
//    private User user;

//    public Product(String name, String description, Double price) {
//        this.name = name;
//        this.description = description;
//        this.price = price;
//    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
