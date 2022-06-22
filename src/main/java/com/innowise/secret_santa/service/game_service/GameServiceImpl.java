package com.innowise.secret_santa.service.game_service;

import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.mapper.GameMapper;
import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.SettingRolesEnum;
import com.innowise.secret_santa.model.dto.request_dto.GameRequestDto;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.response_dto.GameResponseDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.postgres.Game;
import com.innowise.secret_santa.model.postgres.Profile;
import com.innowise.secret_santa.repository.GameRepository;
import com.innowise.secret_santa.repository.specification.GameSpecification;
import com.innowise.secret_santa.service.account_services.AccountGameService;
import com.innowise.secret_santa.service.page_services.PageService;
import com.innowise.secret_santa.service.profile_services.ProfileGamePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService,
        GamePlayerService {
    private final GameRepository gameRepository;
    private final GameMapper gameMapper;
    private final ProfileGamePlayerService profileGameService;
    private final AccountGameService accountGameService;
    private final PageService<GameResponseDto> pageService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           GameMapper gameMapper,
                           ProfileGamePlayerService profileGameService,
                           AccountGameService accountGameService,
                           PageService<GameResponseDto> pageService) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.profileGameService = profileGameService;
        this.accountGameService = accountGameService;
        this.pageService = pageService;
    }

    @Override
    @Transactional
    public GameResponseDto createGame(GameRequestDto game, Long idAccount) {
        return Optional.ofNullable(game)
                .map(gameMapper::toGameFromGameRequestDto)
                .map(this::setTimeCreateToGame)
                .map(item -> setOrganizerToGame(item, idAccount))
                .map(gameRepository::save)
                .map(gameMapper::toGameResponseDto)
                .orElseThrow(() -> new MapperException("Error while created game, please to retry"));
    }

    private Game setTimeCreateToGame(Game game) {
        game.setTimeCreated(LocalDateTime.now());
        return game;
    }

    private Game setOrganizerToGame(Game game, Long idAccount) {
        Profile organizer = Optional.ofNullable(idAccount)
                .map(profileGameService::getProfileByAccountId)
                .orElseThrow(() -> new NoDataFoundException("Profile not found for current account"));
        game.setOrganizer(organizer);
        setOrganizerRoleForAccount(idAccount);
        return game;
    }

    private void setOrganizerRoleForAccount(Long idAccount) {
        accountGameService.setRoleToAccount(idAccount, RoleEnum.ROLE_ORGANIZER, SettingRolesEnum.ADD);
    }

    @Override
    @Transactional(readOnly = true)
    public GameResponseDto getGameById(Long idGame) {
        return Optional.ofNullable(idGame)
                .flatMap(gameRepository::findById)
                .map(gameMapper::toGameResponseDto)
                .orElseThrow(() -> new NoDataFoundException("Game by id: " + idGame + " not found"));
    }

    @Override
    @Transactional
    public void deleteGame(Long idAccount) {
        Game game = Optional.ofNullable(idAccount)
                .map(gameRepository::findGameByOrganizerAccountId)
                .orElseThrow(() -> new NoDataFoundException("Current account isn't organizer"));

        accountGameService.setRoleToAccount(idAccount, RoleEnum.ROLE_ORGANIZER, SettingRolesEnum.DELETE);

        gameRepository.delete(game);
    }

    @Override
    @Transactional
    public GameResponseDto changeGame(GameRequestDto game, Long idAccount) {
        return Optional.ofNullable(idAccount)
                .map(gameRepository::findGameByOrganizerAccountId)
                .map(oldGame -> setNewDataInGame(oldGame, game))
                .map(gameRepository::save)
                .map(gameMapper::toGameResponseDto)
                .orElseThrow(() -> new MapperException("Filed to change game this account"));
    }

    private Game setNewDataInGame(Game oldGame, GameRequestDto newGame) {

        String nameGame = newGame.getNameGame();
        String description = newGame.getDescription();
        LocalDateTime timeEnd = newGame.getTimeEnd();
        LocalDateTime timeStart = newGame.getTimeStart();

        if (!nameGame.isBlank() && !nameGame.equals(oldGame.getNameGame())) {
            oldGame.setNameGame(nameGame);
        }
        if (!description.isBlank() && !description.equals(oldGame.getDescription())) {
            oldGame.setDescription(description);
        }
        if (!timeEnd.isEqual(oldGame.getTimeEnd()) && timeEnd.isAfter(oldGame.getTimeEnd())) {
            oldGame.setTimeEnd(timeEnd);
        }
        if (!timeStart.isEqual(oldGame.getTimeStart()) && timeStart.isAfter(oldGame.getTimeStart())) {
            oldGame.setTimeStart(timeStart);
        }
        return oldGame;
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<Object> getAllGames(PagesDto pages) {
        Page<GameResponseDto> listGames = gameRepository.findAll(pageService.getPage(pages))
                .map(gameMapper::toGameResponseDto);
        if (listGames.isEmpty()) {
            throw new NoDataFoundException("Games not found");
        }
        return pageService.getPagesDtoResponse(pages, listGames.getContent());
    }

    @Override
    public Game getGameByName(String nameGame) {
        return Optional.ofNullable(nameGame)
                .map(gameRepository::findGameByNameGame)
                .orElseThrow(() -> new NoDataFoundException("Game by name: " + nameGame + " not found"));
    }

    @Override
    public List<GameResponseDto> getAllGamesAccounts(Long idAccount) {
        return Optional.ofNullable(idAccount)
                .map(id -> gameRepository.findAll(GameSpecification.likeAccountId(idAccount)))
                .map(games -> games.stream().map(gameMapper::toGameResponseDto).collect(Collectors.toList()))
                .orElseThrow(() -> new NoDataFoundException("For account by id: " + idAccount + " games not found"));
    }
}