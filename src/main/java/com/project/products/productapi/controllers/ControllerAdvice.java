package com.project.products.productapi.controllers;

import com.project.products.productapi.dtos.ErrorDto;
import com.project.products.productapi.exceptions.InvalidProductIdException;
import com.project.products.productapi.exceptions.ProductNotFoundException;
import com.project.products.productapi.exceptions.ServiceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<ErrorDto> handleInvalidProductIdException(Exception exception) {
        ErrorDto error = new ErrorDto();
        error.setMessage(exception.getMessage());
        error.setErrorCode(HttpStatus.BAD_REQUEST.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFoundException(Exception exception) {
        ErrorDto error = new ErrorDto();
        error.setMessage(exception.getMessage());
        error.setErrorCode(HttpStatus.BAD_REQUEST.toString());

//        log.info("Product Not found. Error message is :: {}", error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDto> illegalArgumentException(Exception exception ) {
        ErrorDto error = new ErrorDto();
        error.setMessage(exception.getMessage());
        error.setErrorCode(HttpStatus.NOT_FOUND.toString());
       // log.error("Illegal Argument Exception :: {}", error.getMessage());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<ErrorDto> internalServerError(ServiceNotFoundException exception) {
        ErrorDto error = new ErrorDto();
        error.setMessage(exception.getMessage());
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
     //   log.error("Internal Server error. Error message is :: {}", error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
