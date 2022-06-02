package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountAuthenticationServiceImpl implements AccountAuthenticationService{

    private final PasswordEncoder encoder;
    private final AccountService service;

    @Autowired
    public AccountAuthenticationServiceImpl(PasswordEncoder encoder,
                                            AccountService service) {
        this.encoder = encoder;
        this.service = service;
    }

    //TODO own exception and check password and login
    public AccountAuthenticationResponse getAuthenticationAccount(RegistrationLoginAccount account) {

        AccountAuthenticationResponse accountByEmail = getAccountAuthByEmail(account.getEmail());
        if (accountByEmail != null && encoder.matches(account.getPassword(), accountByEmail.getPassword())) {

            return accountByEmail;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login or password incorrect");
    }

    public AccountAuthenticationResponse getAccountAuthByEmail(String email){

        return service.getAccountAuthByEmail(email);
    }

}
