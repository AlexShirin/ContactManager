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
import static com.example.ContactManager.utils.Constants.DEFAULT_PAGE_SIZE;

@Transactional
@Service
public class ContactService {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactRepository contactRepository;

    private int page = 0;
    private int pageSize = DEFAULT_PAGE_SIZE;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public ResponseContactDto saveContact(AddRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.mapAddRequestDtoToContact(dto);
        log.info("* Service, saveContact, contact=\n{}", contact);
        List<Contact> one = contactRepository.findAllByEmail(
                contact.getEmail(),
                PageRequest.of(0, DEFAULT_PAGE_SIZE));
        log.info("* Service, saveContact, List<Contact> one=\n{}", one);
        if (!one.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot add contact: another contact found with given pattern\n" +
                            contact.toString() + "At least email must be unique");
        // if id>0 then save will overwrite contact with given id instead of add new contact
        contact.setId(null);
        contactRepository.save(contact);
        log.info("* Service, saveContact, save(contact)=\n{}", contact);
        return ContactMapper.mapper.mapContactToResponseDto(contact);
    }

    public ResponseContactDto updateContactById(AddRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.mapAddRequestDtoToContact(dto);
        log.info("* Service, updateContactById, contact=\n{}", contact);
        Optional<Contact> one = contactRepository.findById(contact.getId());
        log.info("* Service, updateContactById, Optional<Contact> one=\n{}", one);
        if (!one.isPresent())
            throw new ContactNotFoundException(
                    "Cannot updateById contact: no contact found with given id\n" + contact.toString());
        contact.setId(one.get().getId());
        Contact update = contactRepository.save(contact);
        log.info("* Service, updateContactById, save(contact)=\n{}", contact);
        return ContactMapper.mapper.mapContactToResponseDto(update);
    }

    public List<ResponseContactDto> findMatchingContacts(Integer page, Integer pageSize, FindRequestContactDto dto) {
        //Make page >= 0
        this.page = page < 0 ? 0 : page;
        //Make pageSize >= 1 && pageSize <= DEFAULT_PAGE_SIZE
        this.pageSize = pageSize < 1 ? 1 : (pageSize > DEFAULT_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize);

        if (dto == null) {
            List<Contact> list = contactRepository.findAll(PageRequest.of(this.page, this.pageSize)).getContent();
            log.info("* Service, getContacts, contactList=\n{}", list);
            return ContactMapper.mapper.mapContactListToResponseDto(list);
        } else {
            log.info("* Service, findMatchingContacts, dto=\n{}", dto);
            List<Contact> contactsByPattern = findContactsByPattern(dto);
            log.info("* Service, findContactsByPattern, contactsByPattern=\n{}", contactsByPattern);
            return ContactMapper.mapper.mapContactListToResponseDto(contactsByPattern);
        }
    }

    public List<ResponseContactDto> deleteMatchingContacts(FindRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.mapFindRequestDtoToContact(dto);
        log.info("* Service, deleteMatchingContacts, contact=\n{}", contact);
        List<Contact> contactList = findContactsByPattern(dto);
        if (contactList.isEmpty())
            throw new ContactNotFoundException(
                    "Cannot delete contacts: no contact found with given pattern\n" + contact.toString());
        for (Contact c : contactList)
            contactRepository.deleteById(c.getId());
        log.info("* Service, deleteMatchingContacts, deleted=\n{}", contactList);
        return ContactMapper.mapper.mapContactListToResponseDto(contactList);
    }

    public List<Contact> findContactsByPattern(FindRequestContactDto dto) {
        Contact contact = ContactMapper.mapper.mapFindRequestDtoToContact(dto);
        Contact fixedContact = fixContact(contact);
        log.info("* Service utils, findContactsByPattern, fixedContact=\n{}", fixedContact);
        if (fixedContact.getId() > 0L) {
            Optional<Contact> found = contactRepository.findById(fixedContact.getId());
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

