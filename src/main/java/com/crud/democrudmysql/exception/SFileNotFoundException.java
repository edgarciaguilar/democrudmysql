package com.crud.democrudmysql.exception;

public class SFileNotFoundException extends RuntimeException{
    
    public SFileNotFoundException(String message) {
        super(message);
    }

    public SFileNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
