package com.example.ContactManager.Service;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ContactService() {
    }

    @Autowired
    public ContactService(ContactRepository cr) {
        this.contactRepository = cr;
    }

    public List<Contact> getAllContacts() {
        return (List<Contact>) contactRepository.findAll();
    }

    public List<Contact> getLimitedNumberOfContacts(long count) {
        return jdbcTemplate.query("SELECT * FROM contact LIMIT ?", new Object[]{count}, new BeanPropertyRowMapper(Contact.class));
    }

    public Contact saveContact(Contact contact) {
        validateContact(contact);
        return contactRepository.save(contact);
    }

    public Contact updateContact(Contact contact) {
        if (contactRepository.existsById(contact.getId())) {
            validateContact(contact);
            return contactRepository.save(contact);
        } else throw new ContactNotFoundException(contact.getId());
    }

    public void deleteContactByPattern(Contact c) {
        jdbcTemplate.update("DELETE FROM contact WHERE FIRST_NAME=? OR LAST_NAME=? OR PHONE=? OR EMAIL=? OR COMPANY=?",
                new Object[] {c.getFirstName(), c.getLastName(), c.getPhone(), c.getEmail(), c.getCompany()});
    }

    public long count() {
        return contactRepository.count();
    }

    private Contact validateContact(Contact contact) {
        if (!contact.getFirstName().chars().allMatch(Character::isLetter))
            throw new InvalidContactDataException(contact, "'First Name' must contain only letters");
        if (!contact.getLastName().chars().allMatch(Character::isLetter))
            throw new InvalidContactDataException(contact, "'Last Name' must contain only letters");
        if (!contact.getPhone().chars().allMatch(Character::isDigit))
            throw new InvalidContactDataException(contact, "'Phone' must contain only digits");
        if (!isValidEmailAddress(contact.getEmail()))
            throw new InvalidContactDataException(contact, "'E-mail' is invalid");
        if (contact.getCompany().isEmpty())
            throw new InvalidContactDataException(contact, "'Company' can't be empty");
        return contact;
    }

    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public List<Contact> findContact(Contact c) {
        return jdbcTemplate.query("SELECT * FROM contact WHERE FIRST_NAME=? OR LAST_NAME=? OR PHONE=? OR EMAIL=? OR COMPANY=?",
                new Object[] {c.getFirstName(), c.getLastName(), c.getPhone(), c.getEmail(), c.getCompany()},
                new BeanPropertyRowMapper(Contact.class));
    }
}

