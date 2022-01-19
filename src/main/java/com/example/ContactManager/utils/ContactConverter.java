package com.example.ContactManager.utils;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<ContactDto> convertContactList2Dto(List<Contact> contactList) {
        return contactList.stream()
                .map(ContactConverter::convertContact2Dto)
                .collect(Collectors.toList());
    }
}
