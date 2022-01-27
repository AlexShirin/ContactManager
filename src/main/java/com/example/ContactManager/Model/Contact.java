package com.example.ContactManager.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Schema(name="Contact", description="Sample model for the documentation")
//@Data
@Entity
@Table(name = "contact")
public class Contact {
    @Schema(type = "Long", format = "int64", description = "contact id in DB (unique)", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Schema(type = "String", description = "First name", example = "John")
    @Column(name = "first_name")
    private String firstName;

    @Schema(type = "String", description = "Last name", example = "Smith")
    @Column(name = "last_name")
    private String lastName;

    @Schema(type = "String", description = "Phone number", example = "1234567")
    @Column(name = "phone")
    private String phone;

    @Schema(type = "String", description = "Email", example = "email@server.com")
    @Column(name = "email")
    private String email;

    @Schema(type = "String", description = "Company name", example = "Google")
    @Column(name = "company")
    private String company;

    public Contact() {}

    public Contact(String firstName, String lastName, String phone, String email, String company) {
        this.id = 0L;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                "}\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}