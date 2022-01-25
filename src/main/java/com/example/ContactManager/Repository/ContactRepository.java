package com.example.ContactManager.Repository;

import com.example.ContactManager.Model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Transactional
    @Query(value = "SELECT * FROM contact c WHERE to_tsvector(c.first_name) @@ plainto_tsquery(?1) " +
            "UNION SELECT * FROM contact c WHERE to_tsvector(c.last_name) @@ plainto_tsquery(?2) " +
            "UNION SELECT * FROM contact c WHERE to_tsvector(c.phone) @@ plainto_tsquery(?3) " +
            "UNION SELECT * FROM contact c WHERE to_tsvector(c.email) @@ plainto_tsquery(?4) " +
            "UNION SELECT * FROM contact c WHERE to_tsvector(c.company) @@ plainto_tsquery(?5)",
            nativeQuery = true)
    List<Contact> fullTextSearch(String firstName, String lastName, String phone, String email, String company);

    @Transactional
    @Query(value = "UPDATE contact " +
            "SET first_name = :firstName, last_name = :lastName, phone = :phone, email = :email, company = :company " +
            "WHERE id = :id " +
            "RETURNING *",
            nativeQuery = true)
    Contact updateById(Long id, String firstName, String lastName, String phone, String email, String company);

    @Transactional
    List<Contact> findAllByEmail(String email, Pageable pageable);
}
