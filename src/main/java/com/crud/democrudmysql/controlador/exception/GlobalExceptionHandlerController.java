package com.crud.democrudmysql.controlador.exception;

import javax.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    String handleEntityNotFoundException(EntityNotFoundException ex) {
        return "error-404";
    }

}
