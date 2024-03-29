package com.example.ContactManager.Exception;

import com.example.ContactManager.Model.Contact;

public class InvalidContactDataException extends RuntimeException {
    public InvalidContactDataException(Contact contact, String message) {
        super("Invalid Contact Data: \n" + message + "  " + contact);
    }
}
