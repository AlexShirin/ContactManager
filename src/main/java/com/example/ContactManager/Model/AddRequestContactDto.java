package com.example.ContactManager.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class AddRequestContactDto {
    @Positive(message = "id should be valid")
    private Long id;
    @NotBlank(message = "firstName should be valid")
    private String firstName;
    @NotBlank(message = "lastName should be valid")
    private String lastName;
    @Positive(message = "phone should be valid")
    private String phone;
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "company should be valid")
    private String company;

    public AddRequestContactDto() {
    }

    public AddRequestContactDto(
            @Positive Long id,
            @NotBlank String firstName,
            @NotBlank String lastName,
            @Positive String phone,
            @Email String email,
            @NotBlank String company
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    @Override
    public String toString() {
        return "AddRequestContactDto{" +
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
