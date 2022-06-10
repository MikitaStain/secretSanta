package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.IncorrectDataException;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountAuthenticationServiceImpl implements AccountAuthenticationService {


    private final AccountService service;

    @Autowired
    public AccountAuthenticationServiceImpl(AccountService service) {
        this.service = service;
    }

    public AccountAuthenticationResponse getAuthenticationAccount(RegistrationLoginAccount account) {
        AccountAuthenticationResponse accountByEmail = service.getAccountAuthByEmail(account.getEmail());
        if (accountByEmail == null) {
            throw new IncorrectDataException("Email is incorrect: " + account.getEmail());
        }
        service.comparePasswords(account.getPassword(),accountByEmail.getPassword());
        return accountByEmail;
    }
}