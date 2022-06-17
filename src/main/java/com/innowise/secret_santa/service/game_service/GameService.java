package com.innowise.secret_santa.service.game_service;

import com.innowise.secret_santa.model.dto.request_dto.GameRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.GameResponseDto;

public interface GameService {

    GameResponseDto createGame(GameRequestDto game, Long idAccount);

    GameResponseDto getGameById(Long idGame);

    void deleteGame (Long idAccount);
}
