package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.client.StatsWhizPlayerClient;
import com.github.nedgladstone.cardball.client.StatsWhizStatsClient;
import com.github.nedgladstone.cardball.model.*;
import com.github.nedgladstone.cardball.repository.PlayerRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@ExecuteOn(TaskExecutors.IO)
@Controller("/player")
public class PlayerController {
    private static final Logger logger = LoggerFactory.getLogger("com.github.nedgladstone.cardball");

    protected final PlayerRepository playerRepository;
    protected final StatsWhizPlayerClient statsWhizPlayerClient;
    protected final StatsWhizStatsClient statsWhizStatsClient;

    public PlayerController(PlayerRepository playerRepository, StatsWhizPlayerClient statsWhizPlayerClient, StatsWhizStatsClient statsWhizStatsClient) {
        this.playerRepository = playerRepository;
        this.statsWhizPlayerClient = statsWhizPlayerClient;
        this.statsWhizStatsClient = statsWhizStatsClient;
    }

    @Get
    public Iterable<Player> list() {
        return playerRepository.findAll();
    }

    @Get("/{id}")
    public Player find(long id) {
        return findPlayer(id);
    }

    @Put
    public Player create(PlayerDefinition definition) {
        String fullName = definition.getFirstName() + " " + definition.getLastName();

        // TODO NG20210206 Search for player first before adding, and modify if found
        // TODO NG20210206 If user specifies MLB player ID, use that
        Player player = new Player(
                definition.getFirstName(),
                definition.getLastName(),
                definition.getYear(),
                0, '?', '?', 0, 0, 0);

        StatsWhizPlayer statsWhizPlayer = statsWhizPlayerClient.findPlayer(definition.getLastName(), definition.getFirstName());
        if (statsWhizPlayer != null) {
            logger.info("Retrieved StatsWhiz player for " + fullName);
            player.setBattingHand(statsWhizPlayer.getBattingHand());
            player.setThrowingHand(statsWhizPlayer.getThrowingHand());
            player.setPosition(statsWhizPlayer.getPosition());
            logger.info("About to query stats for " + fullName + " with id: " + statsWhizPlayer.getId().longValue() + ", year: " + definition.getYear());
            StatsWhizStats statsWhizStats = statsWhizStatsClient.findStats(statsWhizPlayer.getId().longValue(), definition.getYear());
            if (statsWhizStats != null) {
                logger.info("Retrieved StatsWhiz stats for " + fullName);
                player.setBattingAverage((int)(statsWhizStats.getAvg() * 1000.0));
                player.setPitchingAverage((int)(statsWhizStats.getPitchAvg() * 1000.0));
                player.setEra((int)(statsWhizStats.getPitchEra() * 1000.0));
            } else {
                logger.info("Failed to retrieve StatsWhiz stats for " + fullName);
            }
        } else {
            logger.info("Failed to retrieve StatsWhiz player for " + fullName);
        }

        playerRepository.save(player);
        return player;
    }


    private Player findPlayer(long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            throw new IllegalArgumentException("Game " + id + " does not exist");
        }
        return playerOptional.get();
    }
}
