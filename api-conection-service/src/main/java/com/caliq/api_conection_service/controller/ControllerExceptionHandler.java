package com.caliq.api_conection_service.controller;

import com.caliq.api_conection_service.model.ErrorResponse;
import com.caliq.api_conection_service.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productBotFoundHandler(ProductNotFoundException exception){

        ErrorResponse response =new ErrorResponse();
        response.setMessage(exception.getMessage());
        response.setStatus(404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
