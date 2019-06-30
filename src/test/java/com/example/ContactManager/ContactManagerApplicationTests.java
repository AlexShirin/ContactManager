package com.example.ContactManager;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContactManagerApplicationTests {

	@Autowired
	ContactRepository contactRepository;

	@Test
	public void testAllContacts() {
		List<Contact> books = (List<Contact>) contactRepository.findAll();
		assertFalse(!books.isEmpty());
	}

}
