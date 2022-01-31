package com.example.ContactManager.Service;

import com.example.ContactManager.Exception.InvalidContactDataException;
import com.example.ContactManager.Model.Contact;
import org.junit.jupiter.api.Test;

import static com.example.ContactManager.Service.ContactServiceUtils.fixContact;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContactServiceUtilsTest {

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