package com.innowise.secret_santa.service.address_services;

import com.innowise.secret_santa.mapper.AddressMapper;
import com.innowise.secret_santa.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.innowise.secret_santa.constants.TestConstants.ADDRESS;
import static com.innowise.secret_santa.constants.TestConstants.ADDRESS_DTO;
import static com.innowise.secret_santa.constants.TestConstants.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void should_Get_AddressDto_By_Id() {
        given(addressRepository.findById(ID)).willReturn(Optional.of(ADDRESS));
        given(addressMapper.toAddressDto(ADDRESS)).willReturn(ADDRESS_DTO);

        assertEquals(ADDRESS_DTO, addressService.getAddressDtoById(ID));

        then(addressRepository).should(times(1)).findById(ID);
        then(addressMapper).should(times(1)).toAddressDto(ADDRESS);
    }

}