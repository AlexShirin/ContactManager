package com.example.ContactManager.Model;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class FindRequestContactDto {

    @Positive(message = "id should be valid")
    @Value("0")
    private Long id;

    @NotBlank(message = "firstName should be valid")
    @Value("")
    private String firstName;

    @NotBlank(message = "lastName should be valid")
    @Value("")
    private String lastName;

    @Positive(message = "phone should be valid")
    @Value("")
    private String phone;

    @Email(message = "email should be valid")
    @Value("")
    private String email;

    @NotBlank(message = "company should be valid")
    @Value("")
    private String company;

    public FindRequestContactDto() {
    }

    @Override
    public String toString() {
        return "FindRequestContactDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

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
