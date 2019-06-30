package com.example.ContactManager;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Repository.ContactRepository;
import com.example.ContactManager.Service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ContactManagerApplication /*implements CommandLineRunner*/ {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagerApplication.class, args);
    }
}
