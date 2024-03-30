package com.example.salesmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class SaleException extends RuntimeException {
    private final HttpStatus httpStatus;

    public SaleException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
