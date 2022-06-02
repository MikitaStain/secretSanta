package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;

public interface RegistrationAccountService {

    void encodingPassword(RegistrationLoginAccount account);
}
