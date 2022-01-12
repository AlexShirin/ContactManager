package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    //Get all contacts (GET)
    @GetMapping(value = "")
    private List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    //Get contact by it's ID (GET)
    @GetMapping(value = "/{id}")
    private Contact getContactById(@PathVariable("id") long id) {
        return contactService.getContactById(id);
    }

    //Get first N contacts (GET)
    @GetMapping(value = "/count/{number}")
    private List<Contact> getFirstNContacts(@PathVariable("number") long number) {
        return contactService.getFirstNContacts(number);
    }

    //Add contact (POST)
    @PostMapping(value = "/add")
    private Contact addContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    //Find all contacts matching template (POST)
    @PostMapping(value = "/find")
    private List<Contact> findMatchingContacts(@RequestBody Contact contact) {
        return contactService.findMatchingContacts(contact);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/update")
    private Contact updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    //Delete contact by id (DELETE)
    @DeleteMapping(value = "/delete/{id}")
    private Contact deleteContactById(@PathVariable("id") long id) { return contactService.deleteContactById(id); }

    //Delete all contacts matching template(DELETE)
    @DeleteMapping(value = "/delete")
    private List<Contact> deleteMatchingContacts(@RequestBody Contact contact) {
        return contactService.deleteMatchingContacts(contact);
    }
}
