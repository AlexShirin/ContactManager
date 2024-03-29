package com.example.ContactManager.Model;

import javax.validation.constraints.*;

public class AddRequestContactDto {
    @NotNull(message = "id should be >= 0")
    @Min(value = 0, message = "id should be >= 0")
    private Long id;
    @NotBlank(message = "firstName should not be blank")
    private String firstName;
    @NotBlank(message = "lastName should not be blank")
    private String lastName;
    @NotBlank(message = "phone should be positive number")
    @Min(value = 1, message = "phone should be positive number")
    private String phone;
    @NotBlank(message = "email should be valid")
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "company should not be blank")
    private String company;

    public AddRequestContactDto() {
    }

    public AddRequestContactDto(
            Long id,
            String firstName,
            String lastName,
            String phone,
            String email,
            String company
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
