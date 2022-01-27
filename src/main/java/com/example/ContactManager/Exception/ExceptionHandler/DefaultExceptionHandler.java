package com.example.ContactManager.Exception.ExceptionHandler;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Exception.ContactNotFoundException;
import com.example.ContactManager.Exception.InvalidContactDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
class DefaultExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @ResponseBody
    @ExceptionHandler(ContactNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> ContactNotFoundExceptionHandler(ContactNotFoundException ex) {
        log.error("* ExceptionHandler, ContactNotFoundException: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(InvalidContactDataException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> InvalidContactDataExceptionHandler(InvalidContactDataException ex) {
        log.error("* ExceptionHandler, InvalidContactDataException: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        log.error("* ExceptionHandler, MethodArgumentNotValidException: {}", ex.getMessage());
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", "));
        return new ResponseEntity<>("Request body validation error: " + message,
                HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> ConstraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.error("* ExceptionHandler, ConstraintViolationException: {}", ex.getMessage());
        String message = ex.getConstraintViolations().stream().map(
                    err -> err.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName())
                .collect(java.util.stream.Collectors.joining(", "));
        return new ResponseEntity<>("Request parameter validation error: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST);
    }
}