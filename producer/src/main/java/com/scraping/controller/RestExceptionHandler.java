package com.scraping.controller;

import com.scraping.exceptions.InvalidProductType;
import com.scraping.exceptions.LoadPageError;
import com.scraping.exceptions.NotFoundItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidProductType.class)
    public ResponseEntity<String> invalidType(InvalidProductType exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(LoadPageError.class)
    public ResponseEntity<String> loadPageError(LoadPageError exception){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(exception.getMessage());
    }

    @ExceptionHandler(NotFoundItem.class)
    public ResponseEntity<String> emptyItem(NotFoundItem exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
