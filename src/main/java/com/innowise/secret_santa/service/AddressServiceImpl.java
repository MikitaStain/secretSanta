package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.mapper.AddressMapper;
import com.innowise.secret_santa.model.postgres.Address;
import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public void createdAddress(AddressDto address) {

        addressRepository.save(addressMapper.toAddress(address));
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);

    }

    public AddressDto getAddressDtoById(Long id){
        return Optional.ofNullable(id)
                .map(this::getAddressById)
                .map(addressMapper::toAddressDto)
                .orElseThrow(()->new MapperException("Problems with mapping address"));
    }

    private Address getAddressById(Long id) {

        return Optional.ofNullable(id)
                .flatMap(addressRepository::findById)
                .orElseThrow(() -> new NoDataFoundException("Address by id " + id + " not found"));
    }
}
