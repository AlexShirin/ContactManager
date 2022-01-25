package com.example.ContactManager.utils;

import com.example.ContactManager.Model.Contact;
import com.example.ContactManager.Model.ContactDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper mapper = Mappers.getMapper( ContactMapper.class );

    ContactDto convertContact2Dto(Contact contact);

    Contact convertDto2Contact(ContactDto contactDto);

    List<ContactDto> convertContactList2Dto(List<Contact> contactList);
}
