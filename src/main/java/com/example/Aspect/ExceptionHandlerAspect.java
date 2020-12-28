package com.example.Aspect;

import com.example.Exceptions.CustomException;
import com.example.Exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

public class ExceptionHandlerAspect {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> customerNotFoundException (CustomException customException){

        ErrorResponse errorResponse = new ErrorResponse(customException.getMessage(), HttpStatus.NOT_FOUND.value(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> globalException (Exception globalException){

        ErrorResponse errorResponse = new ErrorResponse(globalException.getMessage(), HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
