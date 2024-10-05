package com.wojtekbier03.productservice.exceptions;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends ProductServiceException {
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}