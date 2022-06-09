package com.innowise.secret_santa.service;

import com.innowise.secret_santa.mapper.SentMessageMapper;
import com.innowise.secret_santa.model.dto.request_dto.SentMessageDto;
import com.innowise.secret_santa.model.mongo.SentMessage;
import com.innowise.secret_santa.util.CalendarUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SentMessageServiceImpl implements SentMessagesService {

    private final SentMessageMapper sentMessageMapper;
    private final MongoTemplate mongoTemplate;
    private static final String NAME_COLLECTION = "sent_messages";

    public SentMessageServiceImpl(SentMessageMapper sentMessageMapper, MongoTemplate mongoTemplate) {
        this.sentMessageMapper = sentMessageMapper;
        this.mongoTemplate = mongoTemplate;
    }

    public void saveSentMessage(SentMessageDto sentMessageDto) {
        Optional.ofNullable(sentMessageDto)
                .map(sentMessageMapper::toSentMessage)
                .map(this::setTimeInSentMessage)
                .map(obj -> mongoTemplate.save(obj, NAME_COLLECTION));
    }

    private SentMessage setTimeInSentMessage(SentMessage sentMessage) {
        sentMessage.setTimeMessage
                (
                        CalendarUtils.convertMilliSecondsToFormattedDate(System.currentTimeMillis())
                );
        return sentMessage;
    }
}