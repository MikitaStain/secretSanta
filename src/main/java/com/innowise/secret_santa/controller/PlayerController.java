package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.request_dto.GameRegistration;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.request_dto.PlayerRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.dto.response_dto.PlayerResponseDto;
import com.innowise.secret_santa.service.player_services.PlayerService;
import com.innowise.secret_santa.util.HandleAuthorities;
import com.innowise.secret_santa.util.ValidationParameter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/accounts/profiles/players")
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
                                                     @RequestBody GameRegistration gameRegistration) {

        ValidationParameter.checkParameterIsEmpty(gameRegistration.getNameGame());

        playerService.savePlayer(gameRegistration,
                player,
                HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get player by id")
    @PreAuthorize("hasPermission('ROLE_ADMIN', authentication.principal.authorities)")
    public ResponseEntity<PlayerResponseDto> getPlayerById(@PathVariable("id") Long id) {

        PlayerResponseDto playerById = playerService.getPlayerById(id);
        return new ResponseEntity<>(playerById, HttpStatus.OK);
    }

    @DeleteMapping
    @ApiOperation("Delete player in game by game name")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<HttpStatus> deletePlayerByGameName(@RequestParam String nameGame) {

        ValidationParameter.checkParameterIsEmpty(nameGame);

        playerService.deletePlayerByNameGame(nameGame, HandleAuthorities.getIdAuthenticationAccount());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    @ApiOperation("Get all players")
    @PreAuthorize("hasPermission('ROLE_ADMIN', authentication.principal.authorities)")
    public ResponseEntity<Object> getAllPlayers
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "email") String sort) {

        PagesDtoResponse<Object> allPlayers = playerService.getAllPlayers(
                PagesDto
                        .builder()
                        .sort(sort)
                        .size(size)
                        .page(page)
                        .build());
        return new ResponseEntity<>(allPlayers, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation("Change player")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlayerResponseDto> changePlayer(@RequestBody PlayerRequestDto player,
                                                          @RequestParam String nameGame) {

        ValidationParameter.checkParameterIsEmpty(nameGame);

        PlayerResponseDto playerResponseDto =
                playerService.changePlayer(player, HandleAuthorities.getIdAuthenticationAccount(), nameGame);
        return new ResponseEntity<>(playerResponseDto, HttpStatus.OK);
    }

    @GetMapping("/currentGame")
    @ApiOperation("Get all players from game")
    @PreAuthorize("hasPermission('ROLE_ORGANIZER', authentication.principal.authorities)")
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayersFromGame(@RequestParam String nameGame) {

        ValidationParameter.checkParameterIsEmpty(nameGame);

        List<PlayerResponseDto> players =
                playerService.getAllPlayersFromGame(nameGame, HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get current players")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<PlayerResponseDto>> getAllPlayersForCurrentAccount() {

        List<PlayerResponseDto> currentPlayers =
                playerService.getCurrentPlayers(HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(currentPlayers, HttpStatus.OK);
    }
}
