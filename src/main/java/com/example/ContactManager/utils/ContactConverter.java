package com.example.ContactManager.utils;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;

public class ContactConverter {
    public static ContactDto convertContact2Dto(Contact contact) {
        return new ContactDto(
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getCompany()
        );
    }

    public static Contact convertDto2Contact(ContactDto contactDto) {
        return new Contact(
                contactDto.getFirstName(),
                contactDto.getLastName(),
                contactDto.getPhone(),
                contactDto.getEmail(),
                contactDto.getCompany()
        );
    }
}
