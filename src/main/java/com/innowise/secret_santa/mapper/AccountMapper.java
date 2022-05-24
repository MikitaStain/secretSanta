package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toAccount(AccountDto accountDto);

    AccountDto toAccountDto (Account account);
}
