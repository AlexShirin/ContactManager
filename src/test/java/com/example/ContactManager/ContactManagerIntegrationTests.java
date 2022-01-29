package com.example.ContactManager;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest(classes = ContactManagerApplication.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactManagerIntegrationTests {

    @Autowired
    private ObjectMapper objectMapper;

    WebTestClient webTestClient = WebTestClient.bindToController(ContactController.class).build();


    @Disabled
    @Test
    public void testAllContacts() throws JsonProcessingException {
//		String json = this.objectMapper.writeValueAsString(new Contact(1L, "", "", "", "", ""));

        this.webTestClient
                .get()
                .uri("http://localhost:8080/contact")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectHeader()
                .contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("duke")
                .jsonPath("$[0].tags").isNotEmpty();
    }

    @Disabled
    @Test
    void shouldCreateNewUser() {

        Contact contact = new Contact(1L, "", "", "", "", "");

        FluxExchangeResult<Void> userCreationResponse = this.webTestClient
                .post()
                .uri("/api/users")
                .body(Mono.just(contact), Contact.class)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(CREATED)
                .returnResult(Void.class);

        String locationUrlOfNewUser = userCreationResponse.getResponseHeaders().get(LOCATION).get(0);

        this.webTestClient
                .get()
                .uri(locationUrlOfNewUser)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus()
                .isEqualTo(OK)
                .expectBody(Contact.class)
                .isEqualTo(contact);

        this.webTestClient
                .delete()
                .uri("/api/users/{id}", contact.getId())
                .exchange();
    }
}
