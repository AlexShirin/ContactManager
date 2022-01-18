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

import static com.example.ContactManager.Service.ContactServiceUtils.*;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final int DEFAULT_PAGE_SIZE = 10;

    @PostConstruct
    private void init() { initContacts(contactRepository); }

    @Autowired
    private ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Contact getContactById(long id) {
        return contactRepository.findById(id).get();
    }

    public List<Contact> getFirstNContacts(Long limit) {
        int pageSize;
        if (limit == null || limit > DEFAULT_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        } else if (limit < 1) {
            pageSize = 1;
        } else {
            pageSize = limit.intValue();
        }
        return contactRepository
                .findAll(PageRequest.of(0, pageSize))
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

