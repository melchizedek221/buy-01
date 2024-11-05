package com.secke.product_service.Error;

import org.springframework.http.HttpStatus;

// Resource not found
public class ResourceNotFoundException extends BaseException {
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
