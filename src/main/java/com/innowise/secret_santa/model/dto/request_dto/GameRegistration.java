package com.innowise.secret_santa.model.dto.request_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GameRegistration {

    private String nameGame;

    private String password;
}
