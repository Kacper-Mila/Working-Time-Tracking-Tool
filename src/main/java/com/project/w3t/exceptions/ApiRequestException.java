package com.project.w3t.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiRequestException extends RuntimeException{
    public ApiRequestException(String message) {
        super(message);
    }
}