package com.example.ContactManager.Repository;

import com.example.ContactManager.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact deleteById(long id);
    List<Contact> findByIdOrFirstNameOrLastNameOrPhoneOrEmailOrCompany(long id, String firstName, String lastName, String phone, String email, String company);
}
