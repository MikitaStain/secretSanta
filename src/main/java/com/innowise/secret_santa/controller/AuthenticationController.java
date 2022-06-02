package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import com.innowise.secret_santa.security.JwtToken;
import com.innowise.secret_santa.service.AccountAuthenticationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api("Authentication account")
public class AuthenticationController {

    private final AccountAuthenticationService service;
    private final JwtToken token;

    public AuthenticationController(AccountAuthenticationService service, JwtToken token) {
        this.service = service;
        this.token = token;
    }


    @PostMapping("/login")
    @ApiOperation("Authentication")
    public ResponseEntity<HttpStatus> authenticationAccount(@RequestBody RegistrationLoginAccount account) {
        HttpHeaders responseHeader = new HttpHeaders();
        AccountAuthenticationResponse authenticationAccount = service.getAuthenticationAccount(account);
        responseHeader.set("Authorization", token.getJWTToken(authenticationAccount.getEmail(), authenticationAccount.getRole().getRoleName().getRole()));

        return ResponseEntity.ok().headers(responseHeader).body(HttpStatus.OK);
    }
}