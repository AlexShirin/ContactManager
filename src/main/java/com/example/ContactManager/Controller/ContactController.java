package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.ContactDto;
import com.example.ContactManager.Service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //Get all contacts (GET) if no param or
    //Get first N contacts (GET) if param is present
    @GetMapping(value = "")
    private List<ContactDto> getFirstNContacts(
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long pagesize
    ) {
        log.info("* Controller, GET, page={}, pagesize={}", page, pagesize);
        return contactService.getFirstNContacts(page, pagesize);
    }

    //Find all contacts matching template (GET)
    @GetMapping(value = "/find")
    private List<ContactDto> findMatchingContacts(@RequestBody ContactDto contactDto) {
        log.info("* Controller, findMatchingContacts, POST, contactDto={}", contactDto);
        return contactService.findMatchingContacts(contactDto);
    }

    //Add contact (POST)
    @PostMapping(value = "/add")
    private ContactDto addContact(@RequestBody ContactDto contactDto) {
        log.info("* Controller, addContact, POST, contactDto={}", contactDto);
        return contactService.saveContact(contactDto);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/update")
    private ContactDto updateContact(@RequestBody ContactDto contactDto) {
        log.info("* Controller, updateContact, PUT, contactDto={}", contactDto);
        return contactService.updateContact(contactDto);
    }

    //Delete all contacts matching template (DELETE)
    @DeleteMapping(value = "/delete")
    private List<ContactDto> deleteMatchingContacts(@RequestBody ContactDto contactDto) {
        log.info("* Controller, deleteMatchingContacts, DELETE, contactDto={}", contactDto);
        return contactService.deleteMatchingContacts(contactDto);
    }
}
