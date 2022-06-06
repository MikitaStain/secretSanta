package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.Message;
import com.innowise.secret_santa.model.dto.response_dto.MessageDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    Message toMessage(MessageDtoResponse message);

    MessageDtoResponse toMessageDto(Message message);
}
