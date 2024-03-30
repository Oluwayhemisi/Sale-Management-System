package com.example.salesmanagementsystem.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class ClientException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ClientException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
