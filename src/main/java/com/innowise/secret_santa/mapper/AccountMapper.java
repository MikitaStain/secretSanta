package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.dto.AccountDto;
import com.innowise.secret_santa.model.dto.request_dto.RegistrationLoginAccount;
import com.innowise.secret_santa.model.dto.response_dto.AccountAuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "profile", ignore = true)
    @Mapping(target = "messages", ignore = true)
    Account toAccount(RegistrationLoginAccount accountDto);


    AccountDto toAccountDto(Account account);

    AccountAuthenticationResponse toAccountAuthenticationResponse(Account account);
}
