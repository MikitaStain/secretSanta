package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.request_dto.GameRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.GameResponseDto;
import com.innowise.secret_santa.service.game_service.GameService;
import com.innowise.secret_santa.util.HandleAuthorities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/game")
@Api("Controller about game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @ApiOperation("Create new game")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GameResponseDto> createGame(@RequestBody GameRequestDto game) {

        GameResponseDto newGame = gameService.createGame(game, HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @DeleteMapping
    @ApiOperation("Delete game for current account")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> deleteGame(){
        gameService.deleteGame(HandleAuthorities.getIdAuthenticationAccount());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
