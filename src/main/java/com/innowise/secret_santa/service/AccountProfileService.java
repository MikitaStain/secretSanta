package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.postgres.Account;

public interface AccountProfileService {

    Account getAccountById(Long id);
}
