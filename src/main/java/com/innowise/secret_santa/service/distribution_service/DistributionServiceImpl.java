package com.innowise.secret_santa.service.distribution_service;

import com.innowise.secret_santa.exception.NoDataFoundException;
import com.innowise.secret_santa.model.TypeMessage;
import com.innowise.secret_santa.model.postgres.Distribution;
import com.innowise.secret_santa.model.postgres.Game;
import com.innowise.secret_santa.model.postgres.Player;
import com.innowise.secret_santa.repository.DistributionRepository;
import com.innowise.secret_santa.service.game_service.GameDistributionService;
import com.innowise.secret_santa.service.logger_services.LoggerService;
import com.innowise.secret_santa.service.message_services.SystemMessageService;
import com.innowise.secret_santa.util.CalendarUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class DistributionServiceImpl implements DistributionService {

    private final GameDistributionService gameDistributionService;
    private final DistributionRepository distributionRepository;
    private final SystemMessageService sentMessagesService;

    private final LoggerService<Long> loggerService;


    public DistributionServiceImpl(GameDistributionService gameDistributionService,
                                   DistributionRepository distributionRepository,
                                   SystemMessageService sentMessagesService,
                                   LoggerService<Long> loggerService) {
        this.gameDistributionService = gameDistributionService;
        this.distributionRepository = distributionRepository;
        this.sentMessagesService = sentMessagesService;
        this.loggerService = loggerService;
    }


    @Scheduled(fixedRate = 60000)
    public void createDistributions() {
        List<Game> allGamesAfterCurrentDate = gameDistributionService.getAllGamesAfterCurrentDate();
        for (Game game : allGamesAfterCurrentDate) {
            distributionRepository.saveAll(distribution(game));
            gameDistributionService.changeStatusGameInFinish(game);
            loggerService.loggerInfo("Distribution for game: {0} finished", game.getNameGame());
        }
    }

    private List<Distribution> distribution(Game game) {

        List<Player> players = game.getPlayers();

        if (players.isEmpty() || players.size() == 1) {
            throw new NoDataFoundException("Not enough players in the game: " + game.getNameGame());
        }

        Collections.shuffle(players);
        List<Distribution> distributions = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            if (i + 1 != players.size()) {
                distributions.add(buildDistribution(players.get(i), players.get(i + 1), game));
                sendMessageAboutDistribution(players.get(i), players.get(i + 1));
            } else {
                distributions.add(buildDistribution(players.get(i), players.get(0), game));
                sendMessageAboutDistribution(players.get(i), players.get(0));
            }
        }
        return distributions;
    }

    private Distribution buildDistribution(Player from, Player to, Game game) {
        loggerService.loggerInfo("Player: {0} have to present player: {1} from game: {2}"
                , from.getId(), to.getId(), game.getNameGame());

        return Distribution.builder()
                .game(game)
                .senderPlayer(from)
                .targetPlayer(to)
                .timeCreated(CalendarUtils.getFormatDate(LocalDateTime.now()))
                .build();
    }

    private void sendMessageAboutDistribution(Player fromGift, Player toGift) {

        sentMessagesService.messageService(TypeMessage.DISTRIBUTION, fromGift.getProfile().getAccount().getId(),
                fromGift.getProfile().getAccount().getEmail(), toGift.getProfile().getAccount().getEmail());

        loggerService.loggerInfo("Account by email {}, get email about your player",
                fromGift.getProfile().getAccount().getEmail());
    }
}