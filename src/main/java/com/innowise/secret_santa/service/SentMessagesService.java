package com.innowise.secret_santa.service;

import com.innowise.secret_santa.model.dto.request_dto.SentMessageDto;

public interface SentMessagesService {
    void saveSentMessage(SentMessageDto sentMessageDto);
}
