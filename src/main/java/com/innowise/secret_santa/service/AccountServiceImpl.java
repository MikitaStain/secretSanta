package com.innowise.secret_santa.service;

import com.innowise.secret_santa.exception.IncorrectDataException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.exception.SaveDataException;
import com.innowise.secret_santa.mapper.AccountMapper;
import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.TypeMessage;
import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.AccountChangePassword;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.postgres.Account;
import com.innowise.secret_santa.repository.AccountRepository;
import com.innowise.secret_santa.repository.RoleRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;
    private final PageService<AccountDto> pageService;
    private final Logger logger;
    private final SystemMessageService messageService;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              RoleRepository roleRepository,
                              AccountMapper accountMapper,
                              PasswordEncoder encoder,
                              PageService<AccountDto> pageService,
                              Logger logger,
                              SystemMessageService messageService) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountMapper = accountMapper;
        this.encoder = encoder;
        this.pageService = pageService;
        this.logger = logger;
        this.messageService = messageService;
    }

    @Override
    @Transactional
    public void createdAccount(RegistrationLoginAccount account) {

        checkEmail(account.getEmail());
        Account present = Optional.of(account)
                .map(accountMapper::toAccount)
                .map(this::encodingPassword)
                .map(this::setRoleForAccount)
                .map(this::setDateCreated)
                .map(accountRepository::save)
                .orElseThrow(() -> new SaveDataException("failed to created Account"));

        messageService.messageService(TypeMessage.CREATED, present.getId(), present.getEmail());
        logger.info("Account by email {} successful registration", present.getEmail());
    }

    private Account encodingPassword(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        return account;
    }

    private void checkEmail(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw new IncorrectDataException("Email " + email + " already use");
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

    @Override
    @Transactional(readOnly = true)
    public AccountAuthenticationResponse getAccountAuthByEmail(String email) {
        return Optional.ofNullable(email)
                .map(accountRepository::findAccountByEmail)
                .map(accountMapper::toAccountAuthenticationResponse)
                .orElseThrow(() -> new IncorrectDataException("Email by name '" + email + "' incorrect"));
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {
        accountRepository.delete(getAccountById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountDtoById(Long id) {
        return accountMapper.toAccountDto(getAccountById(id));
    }

    private Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoDataFoundException("Account by id '" + id + "' not found"));
    }

    @Override
    @Transactional
    public AccountDto changePasswordAccount(Long id, AccountChangePassword account) {

        Account accountById = getAccountById(id);
        if (!encoder.matches(account.getOldPassword(), accountById.getPassword())) {
            throw new IncorrectDataException("Password incorrect, please write it again");
        }
        accountById.setPassword(account.getNewPassword());
        accountRepository.save(encodingPassword(accountById));
        logger.info("Account {} changed password", accountById.getEmail());
        messageService.messageService(TypeMessage.CHANGE_PASSWORD, accountById.getId(), accountById.getEmail());

        return accountMapper.toAccountDto(accountById);
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<Object> getAllAccounts(PagesDto pages) {
        Page<AccountDto> listAccount = accountRepository.findAll(pageService.getPage(pages))
                .map(accountMapper::toAccountDto);
        if (listAccount.isEmpty()) {
            throw new NoDataFoundException("Accounts not found");
        }
        return pageService.getPagesDtoResponse(pages, listAccount.getContent());
    }
}