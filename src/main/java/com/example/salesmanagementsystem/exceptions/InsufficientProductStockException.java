package com.example.salesmanagementsystem.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientProductStockException extends RuntimeException {
    private final HttpStatus httpStatus;

    public InsufficientProductStockException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
