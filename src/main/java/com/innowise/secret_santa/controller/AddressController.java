package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.AddressDto;
import com.innowise.secret_santa.service.AddressServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
@Api("Address Rest Controller")
public class AddressController {

    private final AddressServiceImpl addressService;

    @Autowired
    public AddressController(AddressServiceImpl addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a address by id")
    public ResponseEntity<AddressDto> getUser(@PathVariable("id") Long id) {



        return new ResponseEntity<>(new AddressDto(), HttpStatus.OK);
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
}