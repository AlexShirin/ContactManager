package com.example.ContactManager.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//enum ContactFieldType {
//    firstName,
//    lastName,
//    phone,
//    email,
//    company
//}

@Data
@Entity
public class Contact {

    private @Id
    @GeneratedValue
    Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String company;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String phone, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    public boolean overlap(Contact contact){
        return (id.equals(contact.getId()) ||
                firstName.equals(contact.getFirstName()) ||
                lastName.equals(contact.getLastName()) ||
                phone.equals(contact.getPhone()) ||
                email.equals(contact.getEmail()) ||
                company.equals(contact.getCompany()));
    }
}