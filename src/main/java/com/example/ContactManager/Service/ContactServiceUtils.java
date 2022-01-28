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
    public static final int DEFAULT_PAGE_SIZE = 10;

    private static final Logger log = LoggerFactory.getLogger(ContactController.class);


    public static Contact validateContact(Contact contact) {
        if (contact.getFirstName() == null || "".equals(contact.getFirstName()))
            throw new InvalidContactDataException(contact, "'First name' can't be empty and must contain only letters\n");
        if (contact.getLastName() == null || "".equals(contact.getLastName()))
            throw new InvalidContactDataException(contact, "'Last name' can't be empty and must contain only letters\n");
        if (contact.getPhone() == null || "".equals(contact.getPhone()) || !contact.getPhone().chars().allMatch(Character::isDigit))
            throw new InvalidContactDataException(contact, "'Phone' can't be empty and must contain only digits\n");
        if (contact.getEmail() == null || !isValidEmail(contact.getEmail()))
            throw new InvalidContactDataException(contact, "'Email' is invalid, permissible email pattern is 'email@server.com'\n");
        if (contact.getCompany() == null || "".equals(contact.getCompany()))
            throw new InvalidContactDataException(contact, "'Company' can't be empty\n");
        return contact;
    }

    public static boolean isValidEmail(String email) {
        String emailTemplate = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        Pattern emailPattern = Pattern.compile(emailTemplate);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

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