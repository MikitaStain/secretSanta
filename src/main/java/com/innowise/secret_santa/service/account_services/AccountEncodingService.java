package com.innowise.secret_santa.service.account_services;

import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;

public interface AccountEncodingService {
    void comparePasswords(String currentPassword, String validPassword);

    AccountAuthenticationResponse getAccountAuthByEmail(String email);
}