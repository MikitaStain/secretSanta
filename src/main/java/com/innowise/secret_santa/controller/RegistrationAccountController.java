package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.service.RegistrationAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api("Registration account")
public class RegistrationAccountController {

    private final RegistrationAccountService service;

    public RegistrationAccountController(RegistrationAccountService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    @ApiOperation("Registration")
    public ResponseEntity<HttpStatus> registrationAccount(@RequestBody RegistrationLoginAccount account) {

        service.encodingPassword(account);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}