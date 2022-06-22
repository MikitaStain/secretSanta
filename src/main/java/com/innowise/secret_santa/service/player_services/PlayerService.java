package com.innowise.secret_santa.service.player_services;

import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.request_dto.PlayerRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.dto.response_dto.PlayerResponseDto;
import org.springframework.transaction.annotation.Transactional;

public interface PlayerService {

    void registrationInGame(String nameGame, PlayerRequestDto playerRequestDto, Long idAccount);

    PlayerResponseDto getPlayerById(Long id);

    void deletePlayerByNameGame(String nameGame);

    @Transactional(readOnly = true)
    PagesDtoResponse<Object> getAllPlayers(PagesDto pages);

    @Transactional
    PlayerResponseDto changePlayer(PlayerRequestDto playerRequestDto, Long idAccount);
}
