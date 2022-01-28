package com.example.ContactManager.Service;

import com.example.ContactManager.Exception.InvalidContactDataException;
import com.example.ContactManager.Model.Contact;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.example.ContactManager.Service.ContactServiceUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

//@Disabled
public class ContactServiceUtilsTest {
//    Assertions reside in org.junit.jupiter.api.Assertions
//    Assumptions reside in org.junit.jupiter.api.Assumptions
//    @Before and @After no longer exist; use @BeforeEach and @AfterEach instead.
//    @BeforeClass and @AfterClass no longer exist; use @BeforeAll and @AfterAll instead.
//    @Ignore no longer exists, use @Disabled or one of the other built-in execution conditions instead
//    @Category no longer exists, use @Tag instead
//    @Rule and @ClassRule no longer exist; superseded by @ExtendWith and @RegisterExtension
//    @RunWith no longer exists, superseded by the extension model using @ExtendWith

    @Test
    public void validateContactTest() {
        Exception ex;

        //valid contact validation test
        Contact contact = new Contact(1L, "a", "a", "1", "a@a.a", "a");
        Contact validatedContact = validateContact(contact);
        assertEquals(contact, validatedContact);

        //invalid contact validation test
        Contact contact2 = new Contact(1L, null, "a", "1", "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact2));
        assertEquals("Invalid Contact Data: \n" +
                "'First name' can't be empty and must contain only letters\n" + "  " + contact2, ex.getMessage());
        Contact contact21 = new Contact(1L, "", "a", "1", "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact21));
        assertEquals("Invalid Contact Data: \n" +
                "'First name' can't be empty and must contain only letters\n" + "  " + contact21, ex.getMessage());

        Contact contact3 = new Contact(1L, "a", null, "1", "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact3));
        assertEquals("Invalid Contact Data: \n" +
                "'Last name' can't be empty and must contain only letters\n" + "  " + contact3, ex.getMessage());
        Contact contact31 = new Contact(1L, "a", "", "1", "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact31));
        assertEquals("Invalid Contact Data: \n" +
                "'Last name' can't be empty and must contain only letters\n" + "  " + contact31, ex.getMessage());

        Contact contact4 = new Contact(1L, "a", "a", null, "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact4));
        assertEquals("Invalid Contact Data: \n" +
                "'Phone' can't be empty and must contain only digits\n" + "  " + contact4, ex.getMessage());
        Contact contact41 = new Contact(1L, "a", "a", "", "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact41));
        assertEquals("Invalid Contact Data: \n" +
                "'Phone' can't be empty and must contain only digits\n" + "  " + contact41, ex.getMessage());
        Contact contact5 = new Contact(1L, "a", "a", "a", "a@a.a", "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact5));
        assertEquals("Invalid Contact Data: \n" +
                "'Phone' can't be empty and must contain only digits\n" + "  " + contact5, ex.getMessage());

        Contact contact6 = new Contact(1L, "a", "a", "1", null, "a");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact6));
        assertEquals("Invalid Contact Data: \n" +
                "'Email' is invalid, permissible email pattern is 'email@server.com'\n" + "  " + contact6, ex.getMessage());

        Contact contact7 = new Contact(1L, "a", "a", "1", "a@a.a", null);
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact7));
        assertEquals("Invalid Contact Data: \n" +
                "'Company' can't be empty\n" + "  " + contact7, ex.getMessage());
        Contact contact71 = new Contact(1L, "a", "a", "1", "a@a.a", "");
        ex = assertThrows(InvalidContactDataException.class, () -> validateContact(contact71));
        assertEquals("Invalid Contact Data: \n" +
                "'Company' can't be empty\n" + "  " + contact71, ex.getMessage());
    }

    @Test
    public void fixContactTest() {
        Exception ex;

        ex = assertThrows(InvalidContactDataException.class, () -> fixContact(null));
        assertEquals("Invalid Contact Data: \n" +
                "Contact can't be empty" + "  " + null, ex.getMessage());

        Contact contact = new Contact();
        ex = assertThrows(InvalidContactDataException.class, () -> fixContact(contact));
        assertEquals("Invalid Contact Data: \n" +
                "All contact fields can't be empty" + "  " + contact, ex.getMessage());

        Contact in1 = new Contact(-1L, "", "", "", "", "");
                ex = assertThrows(InvalidContactDataException.class, () -> fixContact(in1));
        assertEquals("Invalid Contact Data: \n" + "All contact fields can't be empty" +
                "  " + in1, ex.getMessage());

        Contact in2 = new Contact(-1L, "a", null, null, null, null);
        Contact out2 = new Contact(0L, "a", "", "", "", "");
        assertEquals(out2, fixContact(in2));

        Contact in3 = new Contact(1L, null, null, null, null, null);
        Contact out3 = new Contact(1L, "", "", "", "", "");
        assertEquals(out3, fixContact(in3));
    }
}