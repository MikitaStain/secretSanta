package com.innowise.secret_santa.service.account_services;

import com.innowise.secret_santa.exception.IncorrectDataException;
import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.exception.SaveDataException;
import com.innowise.secret_santa.mapper.AccountMapper;
import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.SettingRolesEnum;
import com.innowise.secret_santa.model.TypeMessage;
import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.AccountChangePassword;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.postgres.Account;
import com.innowise.secret_santa.model.postgres.Role;
import com.innowise.secret_santa.repository.AccountRepository;
import com.innowise.secret_santa.repository.RoleRepository;
import com.innowise.secret_santa.service.logger_services.LoggerService;
import com.innowise.secret_santa.service.message_services.SystemMessageService;
import com.innowise.secret_santa.service.page_services.PageService;
import com.innowise.secret_santa.util.CalendarUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService,
        AccountProfileService,
        AccountEncodingService,
        AccountRegistrationService,
        AccountGameService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final AccountMapper accountMapper;
    private final PasswordEncoder encoder;
    private final PageService<AccountDto> pageService;
    private final LoggerService<?> logger;
    private final SystemMessageService messageService;


    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              RoleRepository roleRepository,
                              AccountMapper accountMapper,
                              PasswordEncoder encoder,
                              PageService<AccountDto> pageService,
                              LoggerService<?> logger,
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

        messageService.messageService(TypeMessage.CREATE, present.getId(), present.getEmail());
        logger.loggerInfo("Account by email {} successful registration", present.getEmail());
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
        account.setRole(List.of(roleRepository.findRoleByRoleName(RoleEnum.ROLE_USER)));
        return account;
    }

    private Account setDateCreated(Account account) {
        account.setDateCreated(CalendarUtils.getFormatDate(LocalDateTime.now()));
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

        Account account = Optional.of(id)
                .map(this::getAccountById)
                .orElseThrow(() -> new NoDataFoundException("Account by id '" + id + "' not found"));

        accountRepository.delete(account);
        logger.loggerInfo("Account by id {} delete", account.getId());
        messageService.messageService(TypeMessage.DELETE, id, account.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDto getAccountDtoById(Long id) {

        return Optional.ofNullable(id)
                .map(this::getAccountById)
                .map(accountMapper::toAccountDto)
                .orElseThrow(() -> new MapperException("Error mapping from Account to AccountDto"));
    }

    @Override
    public Account getAccountById(Long id) {

        return Optional.ofNullable(id)
                .flatMap(accountRepository::findById)
                .orElseThrow(() -> new NoDataFoundException("Account by id '" + id + "' not found"));
    }

    @Override
    @Transactional
    public AccountDto changePasswordAccount(Long id, AccountChangePassword account) {

        Account accountById = Optional.ofNullable(id)
                .map(this::getAccountById)
                .orElseThrow();

        comparePasswords(account.getOldPassword(), accountById.getPassword());
        accountById.setPassword(account.getNewPassword());
        accountRepository.save(encodingPassword(accountById));
        logger.loggerInfo("Account {} changed password", accountById.getEmail());
        messageService.messageService(TypeMessage.CHANGE_PASSWORD, accountById.getId(), accountById.getEmail());

        return accountMapper.toAccountDto(accountById);
    }

    @Override
    public void comparePasswords(String currentPassword, String validPassword) {
        if (!encoder.matches(currentPassword, validPassword)) {
            throw new IncorrectDataException("Password incorrect, please write it again");
        }
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

    @Override
    public void setRoleToAccount(Long id, RoleEnum role, SettingRolesEnum flag) {

        Account acc = Optional.ofNullable(id)
                .map(this::getAccountById)
                .map(account -> getAccountWithAddRoleOrDeleteRole(account, role, flag))
                .map(accountRepository::save)
                .orElseThrow(() -> new NoDataFoundException("Filed to change role for account by id: " + id));

        logger.loggerInfo("Account by id {} set role - 'ROLE_ORGANIZER'", acc.getId());

    }

    private Account getAccountWithAddRoleOrDeleteRole(Account account, RoleEnum roleEnum, SettingRolesEnum flag) {

        List<Role> role = account.getRole();

        if (flag.equals(SettingRolesEnum.ADD)) {
            addNewRoleToAccount(role, roleEnum);
        }
        if (flag.equals(SettingRolesEnum.DELETE)) {
            deleteRoleFromAccount(role, roleEnum);
        }
        account.setRole(role);
        return account;
    }

    private void addNewRoleToAccount(List<Role> roles, RoleEnum roleEnum) {
        if (roles.stream().noneMatch(role -> role.getRoleName().equals(roleEnum))) {
            roles.add(roleRepository.findRoleByRoleName(roleEnum));
        }
    }

    private void deleteRoleFromAccount(List<Role> roles, RoleEnum roleEnum) {
        roles.removeIf(next -> next.getRoleName().equals(roleEnum));
    }
}