package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.ContactDto;
import com.example.ContactManager.Service.ContactService;
import com.example.ContactManager.validation.Marker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
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
    @Validated(Marker.OnGet.class)
    private List<ContactDto> getFirstNContacts(@RequestParam(required = false) Long page, @RequestParam(required = false) Long pagesize) {
        log.info("* Controller, GET, page={}, pagesize={}", page, pagesize);
        return contactService.getFirstNContacts(page, pagesize);
    }

    //Add contact (POST)
    @PostMapping(value = "/add")
    @Validated({Marker.OnAddUpdate.class})
    private ContactDto addContact(@RequestBody @Valid ContactDto contactDto) {
        log.info("* Controller, addContact, POST, contactDto={}", contactDto);
        return contactService.saveContact(contactDto);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/update")
    @Validated(Marker.OnAddUpdate.class)
    private ContactDto updateContact(@RequestBody @Valid ContactDto contactDto) {
        log.info("* Controller, updateContact, PUT, contactDto={}", contactDto);
        return contactService.updateContact(contactDto);
    }

    //Find all contacts matching template (POST)
    @PostMapping(value = "/find")
    @Validated(Marker.OnFindDelete.class)
    private List<ContactDto> findMatchingContacts(@RequestBody ContactDto contactDto) {
        log.info("* Controller, findMatchingContacts, POST, contactDto={}", contactDto);
        return contactService.findMatchingContacts(contactDto);
    }

    //Delete all contacts matching template(DELETE)
    @DeleteMapping(value = "/delete")
    @Validated(Marker.OnFindDelete.class)
    private List<ContactDto> deleteMatchingContacts(@RequestBody ContactDto contactDto) {
        log.info("* Controller, deleteMatchingContacts, DELETE, contactDto={}", contactDto);
        return contactService.deleteMatchingContacts(contactDto);
    }
}
