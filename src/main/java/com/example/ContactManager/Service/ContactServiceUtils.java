package com.example.ContactManager.Service;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Exception.InvalidContactDataException;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactServiceUtils {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    public static Contact fixContact(Contact contact) {
        if (contact == null) throw new InvalidContactDataException(null, "Contact can't be empty");
        if ((contact.getId() == null || (contact.getId() < 1L)) &&
            (contact.getFirstName() == null || "".equals(contact.getFirstName())) &&
            (contact.getLastName() == null || "".equals(contact.getLastName())) &&
            (contact.getPhone() == null || "".equals(contact.getPhone())) &&
            (contact.getEmail() == null || "".equals(contact.getEmail())) &&
            (contact.getCompany() == null || "".equals(contact.getCompany())))
            throw new InvalidContactDataException(contact, "All contact fields can't be empty");

        Long id = contact.getId() == null ? 0L : (contact.getId() < 0 ? 0L : contact.getId());
        String firstName = contact.getFirstName() == null ? "" : contact.getFirstName();
        String lastName = contact.getLastName() == null ? "" : contact.getLastName();
        String phone = contact.getPhone() == null ? "" : contact.getPhone();
        String email = contact.getEmail() == null ? "" : contact.getEmail();
        String company = contact.getCompany() == null ? "" : contact.getCompany();

        return new Contact(id, firstName, lastName, phone, email, company);
    }
}