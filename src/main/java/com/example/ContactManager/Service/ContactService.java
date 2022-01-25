package com.example.ContactManager.Service;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Exception.ContactNotFoundException;
import com.example.ContactManager.Model.*;
import com.example.ContactManager.Repository.ContactRepository;
import com.example.ContactManager.utils.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.ContactManager.Service.ContactServiceUtils.*;

@Transactional
@Service
public class ContactService {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactRepository contactRepository;

//    @PostConstruct
//    private void init() { initContacts(contactRepository); }

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<ResponseContactDto> getFirstNContacts(Long page, Long pagesize) {
        int pageSize;
        if (page == null || page < 0) page = 0L;
        if (pagesize == null || pagesize < 1 || pagesize > DEFAULT_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        } else {
            pageSize = pagesize.intValue();
        }
        log.info("* Service, getFirstNContacts, page={}, pageSize={}", page, pageSize);
        List<Contact> contactList = contactRepository.findAll(PageRequest.of(page.intValue(), pageSize)).getContent();
        log.info("* Service, getFirstNContacts, contactList=\n{}", contactList);
        return ContactMapper.mapper.convertContactListToResponseDto(contactList);
    }

    public ResponseContactDto saveContact(AddRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.convertAddRequestDtoToContact(dto);
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
        return ContactMapper.mapper.convertContactToResponseDto(contact);
    }

    public ResponseContactDto updateContactById(AddRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.convertAddRequestDtoToContact(dto);
        log.info("* Service, updateContactById, contact={}", contact);
        validateContact(contact);
//        List<Contact> one = contactRepository.findAllByEmail(
//                contact.getEmail(),
//                PageRequest.of(0, DEFAULT_PAGE_SIZE));
        Optional<Contact> one = contactRepository.findById(contact.getId());
        log.info("* Service, updateContactById, Optional<Contact> one=\n{}", one);
        if (!one.isPresent())
            throw new ContactNotFoundException(
                    "Cannot updateById contact: no contact found with given id\n" + contact.toString());
//        else if (one.size() >= 2)
//            throw new ContactNotFoundException(
//                    "Cannot updateById contact: multiple contacts found with given pattern\n" + contact.toString());
//        contactRepository.delete(one.get(0));
        contact.setId(one.get().getId());
        Contact update = contactRepository.updateById(contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getCompany());
        log.info("* Service, updateContactById, save(contact)={}", contact);
        return ContactMapper.mapper.convertContactToResponseDto(update);
    }

    public List<ResponseContactDto> findMatchingContacts(FindRequestContactDto dto) {
        log.info("* Service, findMatchingContacts, contactDtoPattern={}", dto);
        List<Contact> contactsByPattern = findContactsByPattern(dto);
        log.info("* Service, findContactsByPattern, contactsByPattern=\n{}", contactsByPattern);
        return ContactMapper.mapper.convertContactListToResponseDto(contactsByPattern);
    }

    public List<ResponseContactDto> deleteMatchingContacts(FindRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.convertFindRequestDtoToContact(dto);
        log.info("* Service, deleteMatchingContacts, contact={}", contact);
        List<Contact> contactList = findContactsByPattern(dto);
        if (contactList.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot delete contacts: no contact found with given pattern\n" + contact.toString());
        for (Contact c : contactList)
            contactRepository.deleteById(c.getId());
        log.info("* Service, deleteMatchingContacts, deleted=\n{}", contactList);
        return ContactMapper.mapper.convertContactListToResponseDto(contactList);
    }

    public List<Contact> findContactsByPattern(FindRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.convertFindRequestDtoToContact(dto);
        Contact fixedContact = fixContact(contact);
        log.info("* Service utils, findContactsByPattern, fixedContact={}", fixedContact);
        if (contact.getId() > 0) {
            Optional<Contact> found = contactRepository.findById(contact.getId());
            return found.map(Collections::singletonList).orElse(null);
        } else
            return contactRepository.fullTextSearch(
                fixedContact.getFirstName(),
                fixedContact.getLastName(),
                fixedContact.getPhone(),
                fixedContact.getEmail(),
                fixedContact.getCompany()
        );
    }
}

