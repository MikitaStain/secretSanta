package com.innowise.secret_santa.model.dto.request_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameRequestDto {

    private LocalDateTime timeStart;

    private LocalDateTime timeEnd;

    private String description;

    private String nameGame;
}
