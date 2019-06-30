package com.example.ContactManager.Service;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(long id) {
        super("Could not find contact id=" + id);
    }
}
