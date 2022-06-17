package com.innowise.secret_santa.service.game_service;

import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.mapper.GameMapper;
import com.innowise.secret_santa.model.RoleEnum;
import com.innowise.secret_santa.model.SettingRolesEnum;
import com.innowise.secret_santa.model.dto.request_dto.GameRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.GameResponseDto;
import com.innowise.secret_santa.model.postgres.Game;
import com.innowise.secret_santa.model.postgres.Profile;
import com.innowise.secret_santa.repository.GameRepository;
import com.innowise.secret_santa.service.account_services.AccountGameService;
import com.innowise.secret_santa.service.profile_services.ProfileGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private final ProfileGameService profileGameService;
    private final AccountGameService accountGameService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           GameMapper gameMapper,
                           ProfileGameService profileGameService, AccountGameService accountGameService) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
        this.profileGameService = profileGameService;
        this.accountGameService = accountGameService;
    }

    @Override
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
    public GameResponseDto getGameById(Long idGame) {
        return Optional.ofNullable(idGame)
                .flatMap(gameRepository::findById)
                .map(gameMapper::toGameResponseDto)
                .orElseThrow(() -> new NoDataFoundException("Game by id: " + idGame + " not found"));
    }

    @Override
    public void deleteGame(Long idAccount) {
        Game game = Optional.ofNullable(idAccount)
                .map(gameRepository::findGameByOrganizerAccountId)
                .orElseThrow(() -> new NoDataFoundException("Current account isn't organizer"));

        accountGameService.setRoleToAccount(idAccount, RoleEnum.ROLE_ORGANIZER, SettingRolesEnum.DELETE);

        gameRepository.delete(game);
    }
}