package com.example.ContactManager.Service;

import com.example.ContactManager.Exception.ContactNotFoundException;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;
import com.example.ContactManager.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.example.ContactManager.Service.ContactServiceUtils.*;
import static com.example.ContactManager.utils.ContactConverter.*;

@Service
public class ContactService {
    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ContactRepository contactRepository;

    @PostConstruct
    private void init() {
        initContacts(contactRepository);
    }

    @Autowired
    private ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ContactDto> getFirstNContacts(Long limit) {
        int pageSize;
        if (limit == null || limit < 1 || limit > DEFAULT_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        } else {
            pageSize = limit.intValue();
        }
        return convertContactList2Dto(contactRepository.findAll(PageRequest.of(0, pageSize)).getContent());
    }

    public ContactDto saveContact(ContactDto contactDto) {
        Contact contact = convertDto2Contact(contactDto);
        validateContact(contact);
        List<Contact> one = contactRepository.findAllByEmail(
                contact.getEmail(),
                PageRequest.of(0, DEFAULT_PAGE_SIZE));
        if (!one.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot add contact: another contact found with given pattern\n" +
                            contact.toString() + "\nAt least email must be unique");
        contactRepository.save(contact);
        return convertContact2Dto(contact);
    }

    public List<ContactDto> findMatchingContacts(ContactDto contactDtoPattern) {
        return convertContactList2Dto(ContactServiceUtils
                .findContactsByPattern(contactRepository, contactDtoPattern));
    }

    public ContactDto updateContact(ContactDto contactDto) {
        Contact contact = convertDto2Contact(contactDto);
        validateContact(contact);
        List<Contact> one = contactRepository.findAllByEmail(
                contact.getEmail(),
                PageRequest.of(0, DEFAULT_PAGE_SIZE));
        if (one.size() != 1)
            throw new ContactNotFoundException(
                    "Cannot update contact: no contact found with given pattern\n" + contact.toString());
        contactRepository.save(contact);
        return convertContact2Dto(contact);
    }

    public List<ContactDto> deleteMatchingContacts(ContactDto contactDtoPattern) {
        Contact contact = convertDto2Contact(contactDtoPattern);
        List<Contact> list = findContactsByPattern(contactRepository, contactDtoPattern);
        if (list.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot delete contacts: no contact found with given pattern\n" + contact.toString());
        for (Contact c : list)
            contactRepository.deleteById(c.getId());
        return convertContactList2Dto(list);
    }
}

