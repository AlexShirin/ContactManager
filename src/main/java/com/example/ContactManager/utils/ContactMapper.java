package com.example.ContactManager.utils;

import com.example.ContactManager.Model.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ContactMapper {
    ContactMapper mapper = Mappers.getMapper( ContactMapper.class );

    Contact mapAddRequestDtoToContact(AddRequestContactDto addRequestContactDto);
    Contact mapFindRequestDtoToContact(FindRequestContactDto findRequestContactDto);

    ResponseContactDto mapContactToResponseDto(Contact contact);
    List<ResponseContactDto> mapContactListToResponseDto(List<Contact> contactList);
}
