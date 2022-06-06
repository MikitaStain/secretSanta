package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.MessageMapper;
import com.innowise.secret_santa.model.Account;
import com.innowise.secret_santa.model.Message;
import com.innowise.secret_santa.model.TypeMessage;
import com.innowise.secret_santa.model.dto.response_dto.MessageDtoResponse;
import com.innowise.secret_santa.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;
    private final MessageMapper mapper;

    public MessageServiceImpl(MessageRepository repository, MessageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<MessageDtoResponse> getAllMessageByAccountId(Long id) {

        return repository
                .findAllByAccount_Id(id)
                .stream()
                .map(mapper::toMessageDto).collect(Collectors.toList());
    }

    public void saveMessage(Account account, String textMessage, TypeMessage type) {

        repository.save(Message
                .builder()
                .textMessage(textMessage)
                .typeMessage(type)
                .account(account)
                .timeMessage(LocalDateTime.now())
                .build());
    }
}

