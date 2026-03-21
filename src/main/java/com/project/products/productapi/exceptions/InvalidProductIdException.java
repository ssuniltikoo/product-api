package com.project.products.productapi.exceptions;

public class InvalidProductIdException extends RuntimeException {

    public InvalidProductIdException(String message) {
        super(message);
    }

}
