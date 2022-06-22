package com.innowise.secret_santa.service.player_services;

import com.innowise.secret_santa.exception.MapperException;
import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.mapper.PlayerMapper;
import com.innowise.secret_santa.model.dto.request_dto.PagesDto;
import com.innowise.secret_santa.model.dto.request_dto.PlayerRequestDto;
import com.innowise.secret_santa.model.dto.response_dto.PagesDtoResponse;
import com.innowise.secret_santa.model.dto.response_dto.PlayerResponseDto;
import com.innowise.secret_santa.model.postgres.Player;
import com.innowise.secret_santa.repository.PlayerRepository;
import com.innowise.secret_santa.service.game_service.GamePlayerService;
import com.innowise.secret_santa.service.logger_services.LoggerService;
import com.innowise.secret_santa.service.page_services.PageService;
import com.innowise.secret_santa.service.profile_services.ProfileGamePlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final GamePlayerService gamePlayerService;
    private final PlayerMapper playerMapper;
    private final ProfileGamePlayerService profileGamePlayerService;
    private final PageService<PlayerResponseDto> pageService;
    private final LoggerService<Long> loggerService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             GamePlayerService gamePlayerService,
                             PlayerMapper playerMapper,
                             ProfileGamePlayerService profileGamePlayerService,
                             PageService<PlayerResponseDto> pageService,
                             LoggerService<Long> loggerService) {
        this.playerRepository = playerRepository;
        this.gamePlayerService = gamePlayerService;
        this.playerMapper = playerMapper;
        this.profileGamePlayerService = profileGamePlayerService;
        this.pageService = pageService;
        this.loggerService = loggerService;
    }

    @Override
    public void registrationInGame(String nameGame, PlayerRequestDto playerRequestDto, Long idAccount) {
        Optional.ofNullable(playerRequestDto)
                .map(playerMapper::toPlayer)
                .map(player -> setGameInPlayer(nameGame, player))
                .map(player -> setProfileInPlayer(idAccount, player))
                .map(this::setDateCreated)
                .map(playerRepository::save)
                .ifPresent(player -> loggerService.logger("Account by id: {}, created player"
                        , player.getProfile().getAccount().getId()));
    }

    private Player setGameInPlayer(String gameName, Player player) {
        player.setGame(Optional.ofNullable(gameName)
                .map(gamePlayerService::getGameByName)
                .orElseThrow(() -> new NoDataFoundException("Game by name: " + gameName + " not found")));
        return player;
    }

    private Player setProfileInPlayer(Long idAccount, Player player) {
        player.setProfile(Optional.ofNullable(idAccount)
                .map(profileGamePlayerService::getProfileByAccountId)
                .orElseThrow(() -> new NoDataFoundException("Profile by account id: " + idAccount + " not found")));
        return player;
    }

    private Player setDateCreated(Player player) {
        player.setTimeRegistration(LocalDateTime.now());
        return player;
    }

    @Override
    public PlayerResponseDto getPlayerById(Long id) {
        return Optional.ofNullable(id)
                .flatMap(playerRepository::findById)
                .map(playerMapper::toPlayerResponseDto)
                .orElseThrow(() -> new NoDataFoundException("Player by id: " + id + " not found"));
    }

    @Override
    public void deletePlayerByNameGame(String nameGame) {
        Optional.ofNullable(nameGame)
                .ifPresent(playerRepository::deletePlayerByGameNameGame);
    }

    @Override
    @Transactional(readOnly = true)
    public PagesDtoResponse<Object> getAllPlayers(PagesDto pages) {
        Page<PlayerResponseDto> listPlayers = playerRepository.findAll(pageService.getPage(pages))
                .map(playerMapper::toPlayerResponseDto);
        if (listPlayers.isEmpty()) {
            throw new NoDataFoundException("Players not found");
        }
        return pageService.getPagesDtoResponse(pages, listPlayers.getContent());
    }

    @Override
    @Transactional
    public PlayerResponseDto changePlayer(PlayerRequestDto playerRequestDto, Long idAccount) {
        return Optional.ofNullable(idAccount)
                .map(playerRepository::findPlayerByProfileAccountId)
                .map(player -> changeDataPlayer(player, playerRequestDto))
                .map(playerRepository::save)
                .map(playerMapper::toPlayerResponseDto)
                .orElseThrow(() -> new MapperException("Error update player"));

    }

    private Player changeDataPlayer(Player player, PlayerRequestDto playerRequestDto) {
        String unnecessaryThings = playerRequestDto.getUnnecessaryThings();
        String necessaryThings = playerRequestDto.getNecessaryThings();

        if (!unnecessaryThings.isBlank() && !unnecessaryThings.equals(player.getUnnecessaryThings())) {
            player.setUnnecessaryThings(unnecessaryThings);
        }
        if (!necessaryThings.isBlank() && !necessaryThings.equals(player.getNecessaryThings())) {
            player.setNecessaryThings(necessaryThings);
        }
        return player;
    }
}
