package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.request_dto.PlayerRequestDto;
import com.innowise.secret_santa.service.player_services.PlayerService;
import com.innowise.secret_santa.util.HandleAuthorities;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/games/player")
@Api("Controller about player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    @ApiOperation("Create player with game")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> registerInGame(@RequestBody PlayerRequestDto player,
                                                     @RequestParam String nameGame) {
        playerService.registrationInGame(nameGame,
                player,
                HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
