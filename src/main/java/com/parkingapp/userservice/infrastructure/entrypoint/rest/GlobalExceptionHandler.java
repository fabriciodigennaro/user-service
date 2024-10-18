package com.parkingapp.userservice.infrastructure.entrypoint.rest;

import com.parkingapp.userservice.infrastructure.entrypoint.rest.response.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
        RuntimeException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse handle(RuntimeException exception) {
        return new ErrorResponse("Internal server error, please try later");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse handle(HttpMessageNotReadableException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
