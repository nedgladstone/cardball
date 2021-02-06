package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.Action;
import com.github.nedgladstone.cardball.model.Game;
import com.github.nedgladstone.cardball.model.Player;
import com.github.nedgladstone.cardball.model.Team;
import com.github.nedgladstone.cardball.repository.GameRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@ExecuteOn(TaskExecutors.IO)
@Controller("/game")
public class GameController {
    protected final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Get("/list")
    public Iterable<Game> list() {
        return gameRepository.findAll();
    }

    @Get("/sneaky")
    public String sneaky() {
        Team rockies = new Team("Colorado", "Rockies", "Ned", "Gladstone",69, 169, null)
                .addPlayer(new Player("Todd", "Helton", 2003, 3, null, 'R', 'R', 308, 999))
                .addPlayer(new Player("Larry", "Walker", 1998, 9, null, 'L', 'L', 297, 999));
        Team phillies = new Team("Philadelphia", "Phillies", "Ed", "Gladstone",100, 87, null)
                .addPlayer(new Player("Greg", "Luzinski", 1978, 7, null, 'R', 'R', 276, 999))
                .addPlayer(new Player("Larry", "Bowa", 1980, 6, null, 'R', 'R', 266, 999));
        Game game7 = new Game(phillies, rockies, 1, 2, 3, 1, 5, null)
                .addAction(new Action(null, null, null, 1, 0, 0, phillies.getPlayers().get(0), new Timestamp(System.currentTimeMillis()), 0, 0, "KL", "", 0, false, true)
                        .addResult(new Action(null, null, null, 0, 0, 0, phillies.getPlayers().get(1), new Timestamp(System.currentTimeMillis()), 1, 2, "PB", "", 2, false, false)));
        gameRepository.save(game7);
        return "Go Rox!";
    }
}
