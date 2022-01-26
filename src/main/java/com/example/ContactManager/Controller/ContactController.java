package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.AddRequestContactDto;
import com.example.ContactManager.Model.FindRequestContactDto;
import com.example.ContactManager.Model.ResponseContactDto;
import com.example.ContactManager.Service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
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

    //Get paged N contacts or find contacts if JSON contact template is present(GET)
    @GetMapping(value = "")
    private List<ResponseContactDto> getFirstNContacts(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestBody(required = false) FindRequestContactDto dto
    ) {
        log.info("* Controller, GET, page={}, pageSize={}", page, pageSize);
        log.info("* Controller, GET, FindRequestContactDto={}", dto);
        return contactService.findMatchingContacts(page, pageSize, dto);
    }

    //Add contact (POST)
    @PostMapping(value = "/add")
    private ResponseContactDto addContact(@Valid @RequestBody AddRequestContactDto dto) {
        log.info("* Controller, addContact, POST, contactDto={}", dto);
        return contactService.saveContact(dto);
    }

    //Update existing contact (PUT)
    @PutMapping(value = "/update")
    private ResponseContactDto updateContactById(@Valid @RequestBody AddRequestContactDto dto) {
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
