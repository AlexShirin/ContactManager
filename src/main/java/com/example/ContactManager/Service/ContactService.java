package com.example.ContactManager.Service;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Exception.ContactNotFoundException;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;
import com.example.ContactManager.Repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);
    private final ContactRepository contactRepository;

    @PostConstruct
    private void init() { initContacts(contactRepository); }

//    @Autowired
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
        log.info("* Service, getFirstNContacts, pageSize={}", pageSize);
        List<Contact> contactList = contactRepository.findAll(PageRequest.of(0, pageSize)).getContent();
        log.info("* Service, getFirstNContacts, contactList=\n{}", contactList);
        return convertContactList2Dto(contactList);
    }

    public ContactDto saveContact(ContactDto contactDto) {
        Contact contact = convertDto2Contact(contactDto);
        log.info("* Service, saveContact, contact={}", contact);
        validateContact(contact);
        List<Contact> one = contactRepository.findAllByEmail(
                contact.getEmail(),
                PageRequest.of(0, DEFAULT_PAGE_SIZE));
        log.info("* Service, saveContact, List<Contact> one=\n{}", one);
        if (!one.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot add contact: another contact found with given pattern\n" +
                            contact.toString() + "At least email must be unique");
        contactRepository.save(contact);
        log.info("* Service, saveContact, save(contact)={}", contact);
        return convertContact2Dto(contact);
    }

    public ContactDto updateContact(ContactDto contactDto) {
        Contact contact = convertDto2Contact(contactDto);
        log.info("* Service, updateContact, contact={}", contact);
        validateContact(contact);
        List<Contact> one = contactRepository.findAllByEmail(
                contact.getEmail(),
                PageRequest.of(0, DEFAULT_PAGE_SIZE));
        log.info("* Service, updateContact, List<Contact> one=\n{}", one);
        if (one.size() == 0)
            throw new ContactNotFoundException(
                    "Cannot update contact: no contact found with given pattern\n" + contact.toString());
        else if (one.size() >= 2)
            throw new ContactNotFoundException(
                    "Cannot update contact: multiple contacts found with given pattern\n" + contact.toString());
        contactRepository.delete(one.get(0));
        contactRepository.save(contact);
        log.info("* Service, updateContact, save(contact)={}", contact);
        return convertContact2Dto(contact);
    }

    public List<ContactDto> findMatchingContacts(ContactDto contactDtoPattern) {
        log.info("* Service, findMatchingContacts, contactDtoPattern={}", contactDtoPattern);
        List<Contact> contactsByPattern = findContactsByPattern(contactRepository, contactDtoPattern);
        log.info("* Service, findContactsByPattern, contactsByPattern=\n{}", contactsByPattern);
        return convertContactList2Dto(contactsByPattern);
    }

    public List<ContactDto> deleteMatchingContacts(ContactDto contactDtoPattern) {
        Contact contact = convertDto2Contact(contactDtoPattern);
        log.info("* Service, deleteMatchingContacts, contact={}", contact);
        List<Contact> contactList = findContactsByPattern(contactRepository, contactDtoPattern);
        if (contactList.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot delete contacts: no contact found with given pattern\n" + contact.toString());
        for (Contact c : contactList)
            contactRepository.deleteById(c.getId());
        log.info("* Service, deleteMatchingContacts, deleted=\n{}", contactList);
        return convertContactList2Dto(contactList);
    }
}

