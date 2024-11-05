package com.secke.product_service.Error;

import org.springframework.http.HttpStatus;

// Bad request
public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
