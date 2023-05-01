package com.practice.git.exception.handler;

import com.practice.git.service.exception.ResourceAlreadyExistException;
import com.practice.git.service.exception.ResourceNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = {ResourceAlreadyExistException.class})
    public ResponseEntity<String> resourceAlreadyExistException(ResourceAlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = {ResourceNotExistException.class})
    public ResponseEntity<String> resourceNotExistException(ResourceNotExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
