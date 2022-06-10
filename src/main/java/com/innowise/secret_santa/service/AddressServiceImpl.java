package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.mapper.AddressMapper;
import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.postgres.Address;
import com.innowise.secret_santa.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final PageService<AddressDto> pageService;
    private final LoggerService<AddressDto> logger;

    private final LoadIdAuthenticationAccount loadId;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository,
                              AddressMapper addressMapper,
                              PageService<AddressDto> pageService,
                              LoggerService<AddressDto> logger,
                              LoadIdAuthenticationAccount loadId) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.pageService = pageService;
        this.logger = logger;
        this.loadId = loadId;
    }

    @Override
    @Transactional
    public void createdAddress(AddressDto address) {

        Optional.ofNullable(address)
                .map(addressMapper::toAddress)
                .map(addressRepository::save)
                .map(addressMapper::toAddressDto)
                .ifPresent(log ->
                        logger.loggerInfo("Account by id :{} added address", loadId.getAccountId())
                );
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        Optional.ofNullable(id)
                .map(this::getAddressById)
                .ifPresent(addressRepository::delete);

        logger.loggerInfo("Address delete for account by id: ", loadId.getAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto getAddressDtoById(Long id) {
        return Optional.ofNullable(id)
                .map(this::getAddressById)
                .map(addressMapper::toAddressDto)
                .orElseThrow(() -> new MapperException("Problems with mapping address"));
    }

    private Address getAddressById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(addressRepository::findById)
                .orElseThrow(() -> new NoDataFoundException("Address by id " + id + " not found"));
    }

    @Override
    @Transactional
    public AddressDto changesAddress(Long id, AddressDto addressDto) {
        return Optional.ofNullable(id)
                .map(this::getAddressById)
                .map(address -> setNewAddressParameter(addressDto, address))
                .map(addressRepository::save)
                .map(addressMapper::toAddressDto)
                .map(item -> logger.logger("Change address for account: "+loadId.getAccountId(), item))
                .orElseThrow(() -> new NoDataFoundException("Error "));
    }

    private Address setNewAddressParameter(AddressDto addressDto, Address address) {

        String country = addressDto.getCountry();
        String city = addressDto.getCity();
        String street = addressDto.getStreet();
        String numberHouse = addressDto.getNumberHouse();
        String numberApartment = addressDto.getNumberApartment();

        if (country != null && !country.equals(address.getCountry())) {
            address.setCountry(country);
        }
        if (city != null && !city.equals(address.getCity())) {
            address.setCity(city);
        }
        if (street != null && !street.equals(address.getStreet())) {
            address.setStreet(street);
        }
        if (numberHouse != null && !numberHouse.equals(address.getNumberHouse())) {
            address.setNumberHouse(numberHouse);
        }
        if (numberApartment != null && !numberApartment.equals(address.getNumberApartment())) {
            address.setNumberApartment(numberApartment);
        }
        return address;
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<Object> getAllAccountsAddress(PagesDto pages) {
        Page<AddressDto> listAddress = addressRepository.findAll(pageService.getPage(pages))
                .map(addressMapper::toAddressDto);
        if (listAddress.isEmpty()) {
            throw new NoDataFoundException("Accounts not found");
        }
        return pageService.getPagesDtoResponse(pages, listAddress.getContent());
    }
}