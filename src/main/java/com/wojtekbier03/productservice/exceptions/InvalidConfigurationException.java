package com.wojtekbier03.productservice.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidConfigurationException extends ProductServiceException {
    public InvalidConfigurationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
