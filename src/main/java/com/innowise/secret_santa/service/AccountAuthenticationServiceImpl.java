package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.IncorrectEmailException;
import com.innowise.secret_santa.exception.IncorrectPasswordException;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountAuthenticationServiceImpl implements AccountAuthenticationService {

    private final PasswordEncoder encoder;
    private final AccountService service;

    @Autowired
    public AccountAuthenticationServiceImpl(PasswordEncoder encoder,
                                            AccountService service) {
        this.encoder = encoder;
        this.service = service;
    }

    public AccountAuthenticationResponse getAuthenticationAccount(RegistrationLoginAccount account) {
        AccountAuthenticationResponse accountByEmail = service.getAccountAuthByEmail(account.getEmail());
        if (accountByEmail == null) {
            throw new IncorrectEmailException("Email is incorrect: " + account.getEmail());
        }
        if (!encoder.matches(account.getPassword(), accountByEmail.getPassword())) {
            throw new IncorrectPasswordException("Password is incorrect!!!");
        }
        return accountByEmail;
    }
}