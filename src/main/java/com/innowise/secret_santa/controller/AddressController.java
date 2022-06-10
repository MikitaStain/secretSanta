package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts/profiles/address")
@Api("Address Rest Controller")
public class AddressController {

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get address by id")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable("id") Long id) {
        AddressDto addressDtoById = addressService.getAddressDtoById(id);
        return new ResponseEntity<>(addressDtoById, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("save a user")
    public ResponseEntity<HttpStatus> createAddress(@RequestBody AddressDto address) {

        addressService.createdAddress(address);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete account by id")
    public ResponseEntity<HttpStatus> deleteAddress(@PathVariable("id") Long id) {

        addressService.deleteAddress(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Change address")
    public ResponseEntity<AddressDto> changeAddressById(@PathVariable Long id,
                                                        @RequestBody AddressDto newAddress) {

        AddressDto address = addressService.changesAddress(id, newAddress);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation("Get all addresses")
    public ResponseEntity<PagesDtoResponse<Object>> getAllAddresses
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "email") String sort) {

        PagesDtoResponse<Object> allAddresses = addressService.getAllAccountsAddress(
                PagesDto
                        .builder()
                        .sort(sort)
                        .size(size)
                        .page(page)
                        .build());

        return new ResponseEntity<>(allAddresses, HttpStatus.OK);
    }
}