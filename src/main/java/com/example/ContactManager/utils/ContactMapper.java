package com.example.ContactManager.utils;

import com.example.ContactManager.Model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper mapper = Mappers.getMapper( ContactMapper.class );

    Contact convertAddRequestDtoToContact(AddRequestContactDto addRequestContactDto);
    Contact convertFindRequestDtoToContact(FindRequestContactDto findRequestContactDto);

    ResponseContactDto convertContactToResponseDto(Contact contact);
    List<ResponseContactDto> convertContactListToResponseDto(List<Contact> contactList);
}
