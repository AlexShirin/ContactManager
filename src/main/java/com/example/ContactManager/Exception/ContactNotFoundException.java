package com.example.ContactManager.Exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(long id) {
        super("Could not find contact id=" + id);
    }
}
