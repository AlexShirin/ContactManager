package com.example.ContactManager.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Contact {

    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String company;

    private Contact() {}

    public Contact(String firstName, String lastName, String phone, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }
}