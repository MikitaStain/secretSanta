package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.TypeMessage;
import com.innowise.secret_santa.model.dto.response_dto.MessageDtoResponse;

import java.util.List;

public interface MessageService {

    void saveMessage(Account account, String textMessage, TypeMessage type);

    List<MessageDtoResponse> getAllMessageByAccountId(Long id);
}
