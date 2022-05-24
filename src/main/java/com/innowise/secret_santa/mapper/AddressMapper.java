package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Address;
import com.innowise.secret_santa.model.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toAddress(AddressDto addressDto);

    AddressDto toAddressDto(Address address);
}
