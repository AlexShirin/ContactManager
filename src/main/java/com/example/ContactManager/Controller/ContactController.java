package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.AddRequestContactDto;
import com.example.ContactManager.Model.FindRequestContactDto;
import com.example.ContactManager.Model.ResponseContactDto;
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
    private List<ResponseContactDto> getFirstNContacts(
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long pagesize
    ) {
        log.info("* Controller, GET, page={}, pagesize={}", page, pagesize);
        return contactService.getFirstNContacts(page, pagesize);
    }

    //Find all contacts matching template (GET)
    @GetMapping(value = "/find")
    private List<ResponseContactDto> findMatchingContacts(@RequestBody FindRequestContactDto dto) {
        log.info("* Controller, findMatchingContacts, POST, contactDto={}", dto);
        return contactService.findMatchingContacts(dto);
    }

    //Add contact (POST)
    @PostMapping(value = "/add")
    private ResponseContactDto addContact(@RequestBody AddRequestContactDto dto) {
        log.info("* Controller, addContact, POST, contactDto={}", dto);
        return contactService.saveContact(dto);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/update")
    private ResponseContactDto updateContactById(@RequestBody AddRequestContactDto dto) {
        log.info("* Controller, updateContactById, PUT, contactDto={}", dto);
        return contactService.updateContactById(dto);
    }

    //Delete all contacts matching template (DELETE)
    @DeleteMapping(value = "/delete")
    private List<ResponseContactDto> deleteMatchingContacts(@RequestBody FindRequestContactDto dto) {
        log.info("* Controller, deleteMatchingContacts, DELETE, contactDto={}", dto);
        return contactService.deleteMatchingContacts(dto);
    }
}
