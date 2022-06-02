package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationAccountServiceImpl implements RegistrationAccountService{

    private final PasswordEncoder passwordEncoder;
    private final AccountService service;

    @Autowired
    public RegistrationAccountServiceImpl(PasswordEncoder passwordEncoder, AccountService service) {
        this.passwordEncoder = passwordEncoder;
        this.service = service;
    }

    @Override
    public void encodingPassword(RegistrationLoginAccount account) {

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        service.createdAccount(account);
    }
}
