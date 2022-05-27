package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.AccountMapper;
import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.Role;
import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.repository.AccountRepository;
import com.innowise.secret_santa.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    @Autowired
    public AccountService(AccountRepository accountRepository,
                          RoleRepository roleRepository,
                          AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountMapper = accountMapper;
    }

    public void createdAccount(RegistrationLoginAccount account){
        Role roleByRoleName = roleRepository.findRoleByRoleName(RoleEnum.ROLE_USER);
        Account account1 = accountMapper.toAccount(account);
        account1.setRole(roleByRoleName);
        accountRepository.save(account1);

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
