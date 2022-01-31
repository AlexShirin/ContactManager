package com.example.ContactManager;

import com.example.ContactManager.Controller.ContactController;
import com.example.ContactManager.Model.Contact;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ContactManagerIntegrationTests {
    private static final Logger log = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
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
        assertThat(body.size()).isEqualTo(4);
    }

    @Test
    @Order(2)
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
    @Order(3)
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
        log.info("* findAllContactsMatchingPatternTest case1, List<Contact>=\n{}", body);
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
        log.info("* findAllContactsMatchingPatternTest case2, List<Contact>=\n{}", body2);
        assertThat(body2).isNotNull();
        assertThat(body2.size()).isEqualTo(1);
    }

    @Test
    @Order(4)
    void CreateUpdateDeleteNewContactTest() {
        Contact contact = new Contact(5L, "a", "b", "5", "aa@a.a", "c");

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
        assertThat(body2.get(0).getFirstName()).isEqualTo("aa");

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
        log.info("* CreateUpdateDeleteNewContactTest delete, Contact=\n{}", body3);
        assertThat(body3).isNotNull();
        assertThat(body3.size()).isEqualTo(1);
        assertThat(body3.get(0).getId()).isEqualTo(contact.getId());
    }
}
