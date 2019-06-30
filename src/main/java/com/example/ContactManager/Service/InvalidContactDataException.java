package com.example.ContactManager.Service;

import com.example.ContactManager.Model.Contact;

public class InvalidContactDataException extends RuntimeException {
    public InvalidContactDataException(Contact contact, String message) {
        super("Invalid Contact Data: " + message + "  " + contact);
    }
}
