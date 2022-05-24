package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.AddressMapper;
import com.innowise.secret_santa.model.Address;
import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public void createdAddress(AddressDto address){

        addressRepository.save(addressMapper.toAddress(address));
    }

    public void deleteAddress(Long id){
        addressRepository.deleteById(id);

    }

    public AddressDto getAddressById(Long id){
        return addressRepository.findById(id)
                .map(addressMapper::toAddressDto)
                .orElseThrow();
    }
}
