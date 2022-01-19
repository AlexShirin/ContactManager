package com.example.ContactManager.Model;

import com.example.ContactManager.validation.Marker;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
//@Validated
public class ContactDto {

//    @NotBlank(groups = Marker.OnAddUpdate.class)
    private String firstName;
//    @NotBlank(groups = Marker.OnAddUpdate.class)
    private String lastName;
//    @Positive(groups = Marker.OnAddUpdate.class)
    private String phone;
//    @Email(groups = Marker.OnAddUpdate.class)
    private String email;
//    @NotBlank(groups = Marker.OnAddUpdate.class)
    private String company;

    public ContactDto() {}

    public ContactDto(String firstName, String lastName, String phone, String email, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.company = company;
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