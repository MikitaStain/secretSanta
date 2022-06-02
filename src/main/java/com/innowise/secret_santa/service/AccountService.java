package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;

public interface AccountService {

    void createdAccount(RegistrationLoginAccount account);

    void deleteAccount(Long id);

    AccountDto getAccountDtoById(Long id);

    AccountAuthenticationResponse getAccountAuthByEmail(String email);
}
