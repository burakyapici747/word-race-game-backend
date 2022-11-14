package com.wordrace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionResult> entityNotFoundException(EntityNotFoundException notFoundException, HttpServletRequest request){
        return ResponseEntity.ok()
                .body(new ExceptionResult(HttpStatus.NOT_FOUND, notFoundException.getMessage(), request.getServletPath(), ZonedDateTime.now()));
    }

    @ExceptionHandler({EntityAlreadyExistException.class})
    public ResponseEntity<ExceptionResult> entityNotFoundException(EntityAlreadyExistException alreadyExistException, HttpServletRequest request){
        return ResponseEntity.ok()
                .body(new ExceptionResult(HttpStatus.BAD_REQUEST, alreadyExistException.getMessage(), request.getServletPath(), ZonedDateTime.now()));
    }

}
