package com.crud.democrudmysql.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SFileNotFoundException extends RuntimeException{
    
    public SFileNotFoundException(String message) {
        super(message);
    }

    public SFileNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
