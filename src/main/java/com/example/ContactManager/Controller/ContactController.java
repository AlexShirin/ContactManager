package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import com.example.ContactManager.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService cs) {
        this.contactService = cs;
    }

    //Get all contacts (GET)
    @GetMapping(value = "/contact")
    private List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    //Get contact by it's ID (GET)
    @GetMapping(value = "/contact/count/{count}")
    private List<Contact> getLimitedNumberOfContacts(@PathVariable("count") long count) {
        return contactService.getLimitedNumberOfContacts(count);
    }

    //Delete contact by it's ID (DELETE)
    @DeleteMapping(value = "/contact/delete")
    private void deleteContact(@RequestBody Contact contact) {
        contactService.deleteContactByPattern(contact);
    }

    //Add contact (POST)
    @PostMapping(value = "/contact/add")
    private Contact addContact(@RequestBody Contact contact) {
        return contactService.saveContact(contact);
    }

    //Find contact (POST)
    @PostMapping(value = "/contact/find")
    private List<Contact> findContact(@RequestBody Contact contact) {
        return contactService.findContact(contact);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/contact/update")
    private Contact updateContact(@RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }
}
