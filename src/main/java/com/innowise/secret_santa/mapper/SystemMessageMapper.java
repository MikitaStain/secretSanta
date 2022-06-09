package com.innowise.secret_santa.mapper;

import com.innowise.secret_santa.model.mongo.SystemMessage;
import com.innowise.secret_santa.model.dto.response_dto.SystemMessageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemMessageMapper {

    SystemMessageDto toSystemMessageDto(SystemMessage systemMessage);
}
