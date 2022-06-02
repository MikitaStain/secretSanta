package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.EmailUsedException;
import com.innowise.secret_santa.exception.IncorrectEmailException;
import com.innowise.secret_santa.exception.NotElementByIdException;
import com.innowise.secret_santa.mapper.AccountMapper;
import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import com.innowise.secret_santa.repository.AccountRepository;
import com.innowise.secret_santa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              RoleRepository roleRepository,
                              AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountMapper = accountMapper;
    }

    public void createdAccount(RegistrationLoginAccount account) {

        checkEmail(account.getEmail());

        Optional.of(account)
                .map(accountMapper::toAccount)
                .map(this::setRoleForAccount)
                .map(this::setDateCreated)
                .map(accountRepository::save);


    }

    private Account getAccountByEmail(String email) {

        return Optional.ofNullable(email)
                .map(accountRepository::findAccountByEmail)
                .orElse(null);
    }

    //TODO maybe check it in validation?
    private void checkEmail(String email) {

        if (getAccountByEmail(email) != null) {
            throw new EmailUsedException("Email '" + email + "' already used");
        }
    }

    private Account setRoleForAccount(Account account) {

        account.setRole(roleRepository.findRoleByRoleName(RoleEnum.ROLE_USER));
        return account;
    }

    private Account setDateCreated(Account account) {

        account.setDateCreated(LocalDateTime.now());
        return account;
    }

    public AccountAuthenticationResponse getAccountAuthByEmail(String email) {

        return Optional.ofNullable(email)
                .map(this::getAccountByEmail)
                .map(accountMapper::toAccountAuthenticationResponse)
                .orElseThrow(() -> new IncorrectEmailException("Email by name '" + email + "' incorrect"));
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.delete(getAccountById(id));

    }

    @Override
    public AccountDto getAccountDtoById(Long id) {
        return accountMapper.toAccountDto(getAccountById(id));
    }

    private Account getAccountById(Long id) {

        return accountRepository.findById(id)
                .orElseThrow(() -> new NotElementByIdException("Account by id '" + id + "' not found"));
    }
}