package com.example.ContactManager.Model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class FindRequestContactDto {
    @Min(value = 0, message = "id should be >= 0")
    private Long id;
    @NotBlank(message = "firstName should not be blank")
    private String firstName;
    @NotBlank(message = "lastName should not be blank")
    private String lastName;
    @Min(value = 1, message = "phone should be positive number")
    private String phone;
    @Email(message = "email should be valid")
    private String email;
    @NotBlank(message = "company should not be blank")
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
        if (id != null) {
            this.id = id;
        } else {
            this.id = 0L;
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null) {
            this.firstName = firstName;
        } else {
            this.firstName = "";
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null) {
            this.lastName = lastName;
        } else {
            this.lastName = "";
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null) {
            this.phone = phone;
        } else {
            this.phone = "";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null) {
            this.email = email;
        } else {
            this.email = "";
        }
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        if (company != null) {
            this.company = company;
        } else {
            this.company = "";
        }
    }
}
