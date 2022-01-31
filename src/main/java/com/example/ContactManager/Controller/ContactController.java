package com.example.ContactManager.Controller;

import com.example.ContactManager.Model.AddRequestContactDto;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.FindRequestContactDto;
import com.example.ContactManager.Model.ResponseContactDto;
import com.example.ContactManager.Service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

import static com.example.ContactManager.utils.Constants.DEFAULT_PAGE_SIZE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "ContactManager", description = "The ContactManager API")
@RestController
@RequestMapping(value = "/contact", produces = APPLICATION_JSON_VALUE)
public class ContactController {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) { this.contactService = contactService; }

    @Operation(summary = "Get contacts", tags = "contact",
            description = "Return specified number of contacts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contact / list of contacts",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(minItems = 1, maxItems = 10,
                                            schema = @Schema(implementation = Contact.class)))}),
                    @ApiResponse(responseCode = "400", description = "Invalid input contact data",
                                 content = @Content(schema = @Schema(implementation = Contact.class)))
            })
    @GetMapping(value = "")
    private List<ResponseContactDto> getContacts(
            @Parameter(name = "page", description = "Page number (page>=0)",
                        schema = @Schema(type = "int", format = "int32", defaultValue = "0"))
            @RequestParam(required = false, defaultValue = "0") Integer page,

            @Parameter(name = "pageSize", description = "Page size (size >= 1 && size <= 10)",
                        schema = @Schema(type = "int", format = "int32", defaultValue = "10"))
            @RequestParam(required = false, defaultValue = "10") Integer pageSize
    ) {
        log.info("* Controller, GET, getContacts, page={}, pageSize={}", page, pageSize);
        return contactService.findMatchingContacts(page, pageSize, null);
    }

    @Operation(summary = "Find contact", tags = "contact",
            description = "Find contact(s) matching pattern in DB",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added contact",
                            content = @Content(schema = @Schema(implementation = Contact.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input contact data",
                            content = @Content(schema = @Schema(implementation = Contact.class)))
            })
    @PostMapping(value = "/find")
    private List<ResponseContactDto> findContacts(
            @Parameter(name = "Contact", description = "Contact data pattern to find in DB",
                    schema = @Schema(implementation = Contact.class))
            @RequestBody FindRequestContactDto dto
    ) {
        log.info("* Controller, findContacts, POST, FindRequestContactDto=\n{}", dto);
        return contactService.findMatchingContacts(0, DEFAULT_PAGE_SIZE, dto);
    }

    @Operation(summary = "Add contact", tags = "contact",
            description = "Add provided contact to DB",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added contact",
                            content = @Content(schema = @Schema(implementation = Contact.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input contact data",
                            content = @Content(schema = @Schema(implementation = Contact.class)))
            })
    @PostMapping(value = "/add")
    private ResponseContactDto addContact(
            @Parameter(name = "Contact", description = "Contact data pattern to add",
                    schema = @Schema(implementation = Contact.class))
            @Valid @RequestBody AddRequestContactDto dto) {
        log.info("* Controller, addContact, POST, AddRequestContactDto=\n{}", dto);
        return contactService.saveContact(dto);
    }

    @Operation(summary = "Update contact", tags = "contact",
            description = "Update provided contact data for contact with given id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Added contact",
                            content = @Content(schema = @Schema(implementation = Contact.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input contact data",
                            content = @Content(schema = @Schema(implementation = Contact.class)))
            })
    @PutMapping(value = "/update")
    private ResponseContactDto updateContactById(
            @Parameter(name = "Contact", description = "Contact data to update",
                    schema = @Schema(implementation = Contact.class))
            @Valid @RequestBody AddRequestContactDto dto) {
        log.info("* Controller, updateContactById, PUT, AddRequestContactDto=\n{}", dto);
        return contactService.updateContactById(dto);
    }

    @Operation(summary = "Delete contact(s)", tags = "contact",
            description = "Delete all contacts matching given template ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Deleted contact(s)",
                            content = {@Content(mediaType = "application/json",
                                    array = @ArraySchema(minItems = 1, maxItems = 10,
                                            schema = @Schema(implementation = Contact.class)))}),
                    @ApiResponse(responseCode = "400", description = "Invalid input contact data",
                            content = @Content(schema = @Schema(implementation = Contact.class)))
            })
    @DeleteMapping(value = "/delete")
    private List<ResponseContactDto> deleteMatchingContacts(
            @Parameter(name = "Contact", description = "Contact data pattern",
                    schema = @Schema(implementation = Contact.class))
            @RequestBody FindRequestContactDto dto) {
        log.info("* Controller, deleteMatchingContacts, DELETE, FindRequestContactDto=\n{}", dto);
        return contactService.deleteMatchingContacts(dto);
    }
}
