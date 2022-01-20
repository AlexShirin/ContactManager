package com.example.ContactManager.Exception.ExceptionHandler;

import com.example.ContactManager.Exception.ContactNotFoundException;
import com.example.ContactManager.Exception.InvalidContactDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
class DefaultExceptionHandler {
    @ResponseBody
    @ExceptionHandler(ContactNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> ContactNotFoundExceptionHandler(ContactNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(InvalidContactDataException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> InvalidContactDataExceptionHandler(InvalidContactDataException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}