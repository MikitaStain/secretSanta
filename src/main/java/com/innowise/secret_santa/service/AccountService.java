package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.AccountMapper;
import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    @Autowired
    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public void createdAccount(AccountDto account){

        accountRepository.save(accountMapper.toAccount(account));
    }

    public void deleteAccount(Long id){
        accountRepository.deleteById(id);

    }

    public AccountDto getAccountById(Long id){
        return accountRepository.findById(id)
                .map(accountMapper::toAccountDto)
                .orElseThrow();
    }
}
