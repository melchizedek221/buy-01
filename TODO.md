# The aim of the project: 
Develop an end-to-end e-commerce platform with Spring Boot microservices and Angular. The platform should support user registration (either as a client or seller), authentication, product CRUD functionality exclusively for sellers, and media management for product images.

###  Database Design
We have 3 entities :
- Media{id: String, imagePath: String, productId: String}
- Product{id: String, name: String, description: String, price: Double, quantity: Int, userId: String}
- User {id: String, name: String, email: String, password: String, client/seller: Enum{Role}, avatar: String}

A user can have multi products but a prodcut is owned by only one user, product can have multi medias and a media is affected only one product.

## THE PROJECT IMPL
We have 3 microservices: User-microservice, Product-microservice, Media-microservice;

- First we will impl the backend api for these 3 microservices.
- Second we will set the communications between them using Euraka server like the service server that every microservice need to register to and kafka to transfert data between these service clients.
