package com.wojtekbier03.productservice.exceptions;

import org.springframework.http.HttpStatus;

public class ConfigurationNotFoundException extends ProductServiceException {
    public ConfigurationNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
