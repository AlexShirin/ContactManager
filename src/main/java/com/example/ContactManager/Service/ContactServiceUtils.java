package com.example.ContactManager.Service;

import com.example.ContactManager.Exception.InvalidContactDataException;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;
import com.example.ContactManager.Repository.ContactRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.ContactManager.utils.ContactConverter.convertContactList2Dto;
import static com.example.ContactManager.utils.ContactConverter.convertDto2Contact;

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
        String emailTemplate = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
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

    public static List<Contact> findContactsByPattern(ContactRepository repository, ContactDto contactDtoPattern) {
        Contact contact = convertDto2Contact(contactDtoPattern);
        Contact fixedContact = fixContact(contact);
        return repository.findAllByFirstNameOrLastNameOrPhoneOrEmailOrCompany(
                fixedContact.getFirstName(),
                fixedContact.getLastName(),
                fixedContact.getPhone(),
                fixedContact.getEmail(),
                fixedContact.getCompany()
        );
    }

    public static void initContacts(ContactRepository repository) {
        repository.save(new Contact(
                "John",
                "Dow",
                "1111",
                "email@mail.com",
                "aaa")
        );
        repository.save(new Contact(
                "James",
                "Dow",
                "2222",
                "email2@mail.com",
                "bbb")
        );
        repository.save(new Contact(
                "John",
                "Smith",
                "3333",
                "email3@mail.com",
                "ccc")
        );
        repository.save(new Contact(
                "James",
                "Smith",
                "4444",
                "email4@mail.com",
                "ddd")
        );
    }
}
