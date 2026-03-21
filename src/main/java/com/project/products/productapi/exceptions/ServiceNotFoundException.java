package com.project.products.productapi.exceptions;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException() {
        super();
    }

    public ServiceNotFoundException(String message) {
        super(message);
    }
}
