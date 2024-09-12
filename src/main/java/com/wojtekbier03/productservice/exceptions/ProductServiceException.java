package com.wojtekbier03.productservice.exceptions;

import org.springframework.http.HttpStatus;

public class ProductServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ProductServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
