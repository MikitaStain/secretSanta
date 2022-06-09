package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.postgres.Address;
import com.innowise.secret_santa.model.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    Address toAddress(AddressDto addressDto);


    AddressDto toAddressDto(Address address);
}
