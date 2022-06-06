package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.AccountChangePassword;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.service.AccountService;
import com.innowise.secret_santa.util.ValidationParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@Api("Account Rest Controller")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{id}")
    @ApiOperation("Get account by id")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id) {

        AccountDto userById = accountService.getAccountDtoById(id);

        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete account by id")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {

        accountService.deleteAccount(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    @ApiOperation("Change password account's")
    public ResponseEntity<AccountDto> changePassword(@PathVariable Long id,
                                                     @RequestBody AccountChangePassword accountChangePassword) {

        ValidationParameter.checkParameterIsEmpty(
                accountChangePassword.getOldPassword(),
                accountChangePassword.getNewPassword(),
                accountChangePassword.getNewPassword2());
        ValidationParameter.checkPassword(
                accountChangePassword.getNewPassword(),
                accountChangePassword.getNewPassword2());

        AccountDto account = accountService.changePasswordAccount(id, accountChangePassword);

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PagesDtoResponse<Object>> getAllAccounts
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "email") String sort){

        PagesDtoResponse<Object> allAccounts = accountService.getAllAccounts(
                PagesDto
                        .builder()
                        .sort(sort)
                        .size(size)
                        .page(page)
                        .build());

        return new ResponseEntity<>(allAccounts,HttpStatus.OK);
    }
}
