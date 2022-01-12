package com.example.ContactManager.Service;

import com.example.ContactManager.Exception.ContactNotFoundException;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.example.ContactManager.Service.ContactServiceUtils.fixContact;
import static com.example.ContactManager.Service.ContactServiceUtils.validateContact;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    @PostConstruct
    private void init() {
        contactRepository.save(new Contact(
                "John",
                "Dow",
                "123456789",
                "email@mail.com",
                "qwe")
        );
        contactRepository.save(new Contact(
                "James",
                "Dow",
                "123456788",
                "email2@mail.com",
                "rty")
        );
        contactRepository.save(new Contact(
                "John",
                "Smith",
                "123456778",
                "email3@mail.com",
                "uio")
        );
    }

    @Autowired
    private ContactService(ContactRepository contactRepository, JdbcTemplate jdbcTemplate) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(long id) {
        return contactRepository.findById(id).get();
    }

    public List<Contact> getFirstNContacts(long number) {
        return contactRepository
                .findAll(PageRequest.of(0, (int) number))
                .getContent();
    }

    public Contact saveContact(Contact contact) {
        Optional<Contact> one = contactRepository.findOne(Example.of(contact));
        if (one.isPresent()) {
            return one.get();
        }
        validateContact(contact);
        return contactRepository.save(contact);
    }

    public List<Contact> findMatchingContacts(Contact contactPattern) {
        Contact fixedContact = fixContact(contactPattern);
        return contactRepository.findByIdOrFirstNameOrLastNameOrPhoneOrEmailOrCompany(
                fixedContact.getId(),
                fixedContact.getFirstName(),
                fixedContact.getLastName(),
                fixedContact.getPhone(),
                fixedContact.getEmail(),
                fixedContact.getCompany()
        );
    }

    public Contact updateContact(Contact contact) {
        if (contactRepository.existsById(contact.getId())) {
            validateContact(contact);
            return contactRepository.save(contact);
        } else
            throw new ContactNotFoundException(contact.getId());
    }

    public Contact deleteContactById(long id) {
        Optional<Contact> one = contactRepository.findById(id);
        if (one.isPresent()) {
            contactRepository.deleteById(id);
            return one.get();
        } else
            throw new ContactNotFoundException(id);
    }

    public List<Contact> deleteMatchingContacts(Contact contactPattern) {
        List<Contact> matchingContacts = findMatchingContacts(contactPattern);
        if (matchingContacts == null) { return null; }
        for (Contact c: matchingContacts) {
            contactRepository.deleteById(c.getId());
        }
        return matchingContacts;
    }
}

