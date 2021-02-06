package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.Player;
import com.github.nedgladstone.cardball.repository.PlayerRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.Optional;

@ExecuteOn(TaskExecutors.IO)
@Controller("/player")
public class PlayerController {
    protected final PlayerRepository playerRepository;

    public PlayerController(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Get("/list")
    public Iterable<Player> list() {
        return playerRepository.findAll();
    }

    @Get("/{id}")
    public Player find(long id) {
        return findPlayer(id);
    }

    private Player findPlayer(long id) {
        Optional<Player> playerOptional = playerRepository.findById(id);
        if (playerOptional.isEmpty()) {
            throw new IllegalArgumentException("Game " + id + " does not exist");
        }
        return playerOptional.get();
    }
}
