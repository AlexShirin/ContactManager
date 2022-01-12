package com.example.ContactManager.Service;

import com.example.ContactManager.Exception.InvalidContactDataException;
import com.example.ContactManager.Model.Contact;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactServiceUtils {
    public static Contact validateContact(Contact contact) {
        if (!contact.getFirstName().chars().allMatch(Character::isLetter))
            throw new InvalidContactDataException(contact, "'First name' must contain only letters");
        if (!contact.getLastName().chars().allMatch(Character::isLetter))
            throw new InvalidContactDataException(contact, "'Last name' must contain only letters");
        if (!contact.getPhone().chars().allMatch(Character::isDigit))
            throw new InvalidContactDataException(contact, "'Phone' must contain only digits");
        if (!isValidEmail(contact.getEmail()))
            throw new InvalidContactDataException(contact, "'Email' is invalid, permissible email pattern is 'email@server.com'");
        if (contact.getCompany().isEmpty())
            throw new InvalidContactDataException(contact, "'Company' can't be empty");
        return contact;
    }

    public static boolean isValidEmail(String email) {
        String emailTemplate = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern emailPattern = Pattern.compile(emailTemplate);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static Contact fixContact(Contact contact) {
        if (contact == null) {
            return new Contact();
        }
        Long id = contact.getId() == null ? 0L : contact.getId();
        String firstName = contact.getFirstName() == null ? "" : contact.getFirstName();
        String lastName = contact.getLastName() == null ? "" : contact.getLastName();
        String phone = contact.getPhone() == null ? "" : contact.getPhone();
        String email = contact.getEmail() == null ? "" : contact.getEmail();
        String company = contact.getCompany() == null ? "" : contact.getCompany();

        Contact validContact = new Contact(firstName, lastName, phone, email, company);
        validContact.setId(id);
        return validContact;
    }
}
