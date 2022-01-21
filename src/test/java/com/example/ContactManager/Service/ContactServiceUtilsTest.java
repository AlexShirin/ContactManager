package com.example.ContactManager.Service;

import com.example.ContactManager.Model.Contact;
import org.junit.Ignore;
import org.junit.Test;

import static com.example.ContactManager.Service.ContactServiceUtils.*;
import static org.junit.Assert.*;

@Ignore
public class ContactServiceUtilsTest {

    @Test
    public void validateContactTest() {
        Contact contact1 = new Contact("a", "a", "1", "a@a.a", "a");
        Contact contact2 = validateContact(contact1);
        assertEquals(contact1, contact2);
    }

    @Test
    public void isValidEmailTest() {
        assertTrue(isValidEmail("aa@aa.aa"));

        assertFalse(isValidEmail("aa.aa"));
        assertFalse(isValidEmail("aa@aa"));
        assertFalse(isValidEmail("aa"));

        assertFalse(isValidEmail(".aa@aa.aa"));
        assertFalse(isValidEmail("a..a@aa.aa"));
        assertFalse(isValidEmail("aa.@aa.aa"));
        assertFalse(isValidEmail("aa@aa..aa"));
        assertFalse(isValidEmail(".aa@.aa.aa"));
        assertFalse(isValidEmail(".aa@aa.aa."));
    }

    @Test
    public void fixContactTest() {
        assertEquals(fixContact(null), new Contact());

        Contact contact = new Contact("", "", "", "", "");
        contact.setId(0L);
        assertEquals(fixContact(new Contact()), contact);
    }
}