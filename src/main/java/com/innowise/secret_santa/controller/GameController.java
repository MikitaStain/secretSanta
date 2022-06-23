package com.innowise.secret_santa.controller;

import com.innowise.secret_santa.model.dto.request_dto.GameRequestDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.GameResponseDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.service.game_service.GameService;
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
@RequestMapping("api/games")
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

        ValidationParameter.checkParameterIsEmpty(game.getNameGame());

        GameResponseDto newGame = gameService.createGame(game, HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(newGame, HttpStatus.CREATED);
    }

    @DeleteMapping
    @ApiOperation("Delete game for current account")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_ORGANIZER')")
    public ResponseEntity<HttpStatus> deleteGame(@RequestParam String nameGame) {
        gameService.deleteGame(HandleAuthorities.getIdAuthenticationAccount(), nameGame);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get game by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GameResponseDto> getGameByID(@PathVariable("id") Long id) {
        GameResponseDto gameById = gameService.getGameById(id);
        return new ResponseEntity<>(gameById, HttpStatus.OK);
    }

    @PatchMapping
    @ApiOperation("Change game for current account")
    @PreAuthorize("hasRole('ROLE_ORGANIZER')")
    public ResponseEntity<GameResponseDto> changeGame(@RequestBody GameRequestDto game,
                                                      @RequestParam String nameGame) {
        GameResponseDto gameResponseDto =
                gameService.changeGame(game, HandleAuthorities.getIdAuthenticationAccount(), nameGame);
        return new ResponseEntity<>(gameResponseDto, HttpStatus.OK);
    }

    @GetMapping
    @ApiOperation("Get all games current account")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<GameResponseDto>> getAllGamesCurrentAccount() {
        List<GameResponseDto> listGames =
                gameService.getAllGamesAccounts(HandleAuthorities.getIdAuthenticationAccount());

        return new ResponseEntity<>(listGames, HttpStatus.OK);
    }

    @GetMapping("/all")
    @ApiOperation("Get all games")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PagesDtoResponse<Object>> getAllAccounts
            (@RequestParam(defaultValue = "5") int size,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(required = false, defaultValue = "email") String sort) {

        PagesDtoResponse<Object> allGames = gameService.getAllGames(PagesDto
                .builder()
                .sort(sort)
                .size(size)
                .page(page)
                .build());
        return new ResponseEntity<>(allGames, HttpStatus.OK);
    }


}
