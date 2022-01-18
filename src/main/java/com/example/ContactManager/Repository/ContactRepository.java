package com.example.ContactManager.Repository;

import com.example.ContactManager.Model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByFirstNameOrLastNameOrPhoneOrEmailOrCompany(String firstName, String lastName, String phone, String email, String company);
    List<Contact> findAllByFirstNameAndLastNameAndPhoneAndEmailAndCompany(String firstName, String lastName, String phone, String email, String company);
    List<Contact> findAllByEmail(String email, Pageable pageable);
}
