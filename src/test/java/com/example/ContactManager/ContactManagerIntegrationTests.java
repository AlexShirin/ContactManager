package com.example.ContactManager;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Model.Contact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactManagerIntegrationTests {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void GetAllContactsTest() throws JsonProcessingException {
        EntityExchangeResult<List<Contact>> response = webTestClient
                .get()
                .uri("/contact") // the base URL is already configured for us
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body = response.getResponseBody();
        log.info("* testGetAllContacts, List<Contact>=\n{}", body);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(5);
    }

    @Test
    void GetAllContactsWithParamTest() {
        EntityExchangeResult<List<Contact>> response = webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/contact")
                        .queryParam("page", "0")
                        .queryParam("pageSize", "3")
                        .build()) // the base URL is already configured for us   "/contact?page=0&pagesize=3"
                .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body = response.getResponseBody();
        log.info("* GetAllContactsWithParamTest, List<Contact>=\n{}", body);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(3);
    }

    @Test
    void findAllContactsMatchingPatternTest() throws JsonProcessingException {
        Contact contact = new Contact(null, "john", null, null, null, null);
        EntityExchangeResult<List<Contact>> response = webTestClient
                .post()
                .uri("/contact/find")
                .body(Mono.just(contact), Contact.class)
                .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body = response.getResponseBody();
        log.info("* findAllContactsMatchingPatternTest c1, List<Contact>=\n{}", body);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(2);

        Contact contact2 = new Contact(1L, null, null, null, null, null);
        EntityExchangeResult<List<Contact>> response2 = webTestClient
                .post()
                .uri("/contact/find")
                .body(Mono.just(contact2), Contact.class)
                .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body2 = response2.getResponseBody();
        log.info("* findAllContactsMatchingPatternTest c2, List<Contact>=\n{}", body2);
        assertThat(body2).isNotNull();
        assertThat(body2.size()).isEqualTo(1);
    }

    @Test
    void CreateUpdateDeleteNewContactTest() {
        Contact contact = new Contact(null, "a", "b", "5", "aa@a.a", "c");

        //add contact
        EntityExchangeResult<List<Contact>> response = webTestClient
                .post()
                .uri("/contact/add")
                .body(Mono.just(contact), Contact.class)
                .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body = response.getResponseBody();
        log.info("* CreateUpdateDeleteNewContactTest add, Contact=\n{}", body);
        assertThat(body).isNotNull();
        assertThat(body.size()).isEqualTo(1);
        assertThat(body.get(0).getId()).isPositive();

        //update contact
        contact.setId(body.get(0).getId());
        contact.setFirstName("aa");
        EntityExchangeResult<List<Contact>> response2 = webTestClient
                .put()
                .uri("/contact/update")
                .body(Mono.just(contact), Contact.class)
                .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body2 = response2.getResponseBody();
        log.info("* CreateUpdateDeleteNewContactTest update, Contact=\n{}", body2);
        assertThat(body2).isNotNull();
        assertThat(body2.size()).isEqualTo(1);
        assertThat(body2.get(0).getFirstName()).isEqualTo("a");

        //delete contact
        EntityExchangeResult<List<Contact>> response3 = webTestClient
                .method(HttpMethod.DELETE)
                .uri("/contact/delete")
                .body(Mono.just(contact), Contact.class)
                .accept(APPLICATION_JSON, APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Contact.class)
                .returnResult();
        List<Contact> body3 = response3.getResponseBody();
        log.info("* CreateUpdateDeleteNewContactTest update, Contact=\n{}", body3);
        assertThat(body3).isNotNull();
        assertThat(body3.size()).isEqualTo(1);
        assertThat(body3.get(0).getId()).isEqualTo(contact.getId());
    }

}

//		String json = this.objectMapper.writeValueAsString(new Contact(1L, "", "", "", "", ""));

//        this.webTestClient
//                .get()
//                .uri("http://localhost:8080/contact")
//                .header(ACCEPT, APPLICATION_JSON_VALUE)
//                .exchange()
//                .expectStatus()
//                .is2xxSuccessful()
//                .expectHeader()
//                .contentType(APPLICATION_JSON)
//                .expectBody()
//                .jsonPath("$.length()").isEqualTo(3)
//                .jsonPath("$[0].id").isEqualTo(1)
//                .jsonPath("$[0].name").isEqualTo("duke")
//                .jsonPath("$[0].tags").isNotEmpty();
//        webClient.get();

//        FluxExchangeResult<Void> userCreationResponse = this.webTestClient
//                .post()
//                .uri("/api/users")
//                .body(Mono.just(contact), Contact.class)
//                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
//                .header(ACCEPT, APPLICATION_JSON_VALUE)
//                .exchange()
//                .expectStatus()
//                .isEqualTo(CREATED)
//                .returnResult(Void.class);
//
//        String locationUrlOfNewUser = userCreationResponse.getResponseHeaders().get(LOCATION).get(0);
//
//        this.webTestClient
//                .get()
//                .uri(locationUrlOfNewUser)
//                .header(ACCEPT, APPLICATION_JSON_VALUE)
//                .exchange()
//                .expectStatus()
//                .isEqualTo(OK)
//                .expectBody(Contact.class)
//                .isEqualTo(contact);
//
//        this.webTestClient
//                .delete()
//                .uri("/api/users/{id}", contact.getId())
//                .exchange();