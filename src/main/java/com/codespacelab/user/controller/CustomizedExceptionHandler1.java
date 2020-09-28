package com.codespacelab.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler1 {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity validationConstraintErrorHandler(ConstraintViolationException ex){
        return new ResponseEntity("constraint violation", HttpStatus.INTERNAL_SERVER_ERROR);
    }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity validationInvalidArgumentErrorHandler(MethodArgumentNotValidException ex){
        return new ResponseEntity("invalid argument", HttpStatus.BAD_REQUEST);
    }
}
