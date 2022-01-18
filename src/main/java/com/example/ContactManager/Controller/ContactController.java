package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;
import com.example.ContactManager.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.ContactManager.utils.ContactConverter.*;

@RestController
@RequestMapping(value = "/contact", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //Get all contacts (GET) if no param or
    //Get first N contacts (GET) if param is present
    @GetMapping(value = "")
    private List<ContactDto> getFirstNContacts(@RequestParam(required = false) Long limit) {
        return contactService.getFirstNContacts(limit);
    }

    //Add contact (POST)
    @PostMapping(value = "/add")
    private ContactDto addContact(@RequestBody ContactDto contactDto) {
        return contactService.saveContact(contactDto);
    }

    //Find all contacts matching template (POST)
    @PostMapping(value = "/find")
    private List<ContactDto> findMatchingContacts(@RequestBody ContactDto contactDto) {
        return contactService.findMatchingContacts(contactDto);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/update")
    private ContactDto updateContact(@RequestBody ContactDto contactDto) {
        return contactService.updateContact(contactDto);
    }

    //Delete all contacts matching template(DELETE)
    @DeleteMapping(value = "/delete")
    private List<ContactDto> deleteMatchingContacts(@RequestBody ContactDto contactDto) {
        return contactService.deleteMatchingContacts(contactDto);
    }
}
