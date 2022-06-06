package com.innowise.secret_santa.model.dto.response_dto;

import com.innowise.secret_santa.model.TypeMessage;
import com.innowise.secret_santa.model.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDtoResponse {

    private String textMessage;

    private LocalDateTime timeMessage;

    private TypeMessage typeMessage;

    private AccountDto account;
}