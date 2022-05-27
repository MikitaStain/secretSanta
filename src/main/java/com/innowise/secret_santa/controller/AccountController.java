package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.service.AccountService;
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
@RequestMapping("/api/accounts")
@Api("Account Rest Controller")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    @ApiOperation("getting a account by id")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id) {

        AccountDto userById = accountService.getAccountById(id);

        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("save a user")
    public ResponseEntity<HttpStatus> createAccount(@RequestBody RegistrationLoginAccount account) {

        accountService.createdAccount(account);

        return new ResponseEntity<>( HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete account by id")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        accountService.deleteAccount(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
