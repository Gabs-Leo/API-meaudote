package com.gabsleo.meaudote.controllers;

import com.gabsleo.meaudote.exceptions.AppUserNotFoundException;
import com.gabsleo.meaudote.utils.Response;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<Response<Object>> handleExceptions(final Exception exception) {
        final Response<Object> response = new Response<>();
        response.getErrors().add(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({AppUserNotFoundException.class})
    public ResponseEntity<Response<Object>> handleNotFoundExceptions(final Exception exception) {
        final Response<Object> response = new Response<>();
        response.getErrors().add(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Response<Object>> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        final Response<Object> response = new Response<>();
        exception.getFieldErrors().forEach(i -> response.getErrors().add(i.getDefaultMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}