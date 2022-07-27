package com.company.rewardspoints.controllers;

import java.util.HashMap;

import com.company.rewardspoints.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
class ErrorControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> constraintExceptionHandler(MethodArgumentNotValidException exception)
    {
        Map<String, String> errorMap = new HashMap<>();
        exception.getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> notFoundExceptionHandler(ResourceNotFoundException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", exception.getMessage());
        return ResponseEntity.badRequest().body(errorMap);
    }
}
