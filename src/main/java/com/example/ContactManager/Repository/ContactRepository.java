package com.example.ContactManager.Repository;

import com.example.ContactManager.Model.Contact;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value = "SELECT * FROM contact c WHERE " +
            "to_tsvector(c.first_name) || to_tsvector(c.last_name) || to_tsvector(c.phone) || to_tsvector(c.email) || to_tsvector(c.company) " +
            "@@ (plainto_tsquery(?1) || plainto_tsquery(?2) || plainto_tsquery(?3) || plainto_tsquery(?4) || plainto_tsquery(?5))",
            nativeQuery = true)
    List<Contact> fullTextSearch(String firstName, String lastName, String phone, String email, String company);

    List<Contact> findAllByEmail(String email, Pageable pageable);
}
