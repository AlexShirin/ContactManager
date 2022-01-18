package com.example.ContactManager.Exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String msg) {
        super("Error: " + msg);
    }
}
