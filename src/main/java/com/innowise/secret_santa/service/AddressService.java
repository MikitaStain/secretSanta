package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;

public interface AddressService {

    AddressDto changesAddress(Long id, AddressDto addressDto);

    AddressDto getAddressDtoById(Long id);

    void createdAddress(AddressDto address);

    PagesDtoResponse<Object> getAllAccountsAddress(PagesDto pages);

    void deleteAddress(Long id);
}
