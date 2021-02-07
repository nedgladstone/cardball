package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.*;
import com.github.nedgladstone.cardball.repository.PlayerRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
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
        // TODO NG20210206 Search for player first before adding, and modify if found
        // TODO NG20210206 If user specifies MLB player ID, use that
        // TODO NG20210206 Look up player info in statswhiz and incorporate info and stats!
        Player player = new Player(definition.getFirstName(), definition.getLastName(), definition.getYear(), 0, '?', '?', 0, 0);
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
