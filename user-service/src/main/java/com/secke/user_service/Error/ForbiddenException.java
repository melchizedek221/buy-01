package com.secke.user_service.Error;

import org.springframework.http.HttpStatus;

// Forbidden
public class ForbiddenException extends BaseException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
