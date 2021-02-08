package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.*;
import com.github.nedgladstone.cardball.repository.GameRepository;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ExecuteOn(TaskExecutors.IO)
@Controller("/game")
public class GameController {
    protected final TeamController teamController;
    protected final PlayerController playerController;
    protected final GameRepository gameRepository;

    public GameController(TeamController teamController, PlayerController playerController, GameRepository gameRepository) {
        this.teamController = teamController;
        this.playerController = playerController;
        this.gameRepository = gameRepository;
    }

    @Get
    public Iterable<Game> list() {
        return gameRepository.findAll();
    }

    @Get("/{id}")
    public Game find(long id) {
        return findGame(id);
    }

    // TODO NG20210207 This should return Game, but that is causing deser errors at client
    @Post
    public Game create(GameDefinition definition) {
        Team visitingTeam = findTeam(definition.getVisitingTeamId());
        Team homeTeam = findTeam(definition.getHomeTeamId());
        Game game = new Game(definition.getName(), visitingTeam, homeTeam);
        gameRepository.save(game);
        return game; //.getId().toString();
    }

    @Get("/{id}/status")
    public GameStatus findStatus(long id) {
        return findGame(id).getStatus();
    }

    @Get("/{gameId}/lineup")
    public LineupsResponse listLineups(long gameId) {
        Game game = findGame(gameId);
        return new LineupsResponse(game.getVisitingLineup(), game.getHomeLineup());
    }

    @Put("/{gameId}/lineup/{side}")
    public GameStatus putLineup(long gameId, String side, LineupDefinition lineupDefinition) {
        Game game = findGame(gameId);
        List<Participant> lineup = lineupDefinition.getParticipants().stream()
                .map(pd -> new Participant(
                        game,
                        pd.getNumberInBattingOrder(),
                        pd.getFieldingPosition(),
                        playerController.find(pd.getPlayerId())))
                .collect(Collectors.toList());
        game.putLineup(Game.Side.fromString(side), lineup);
        gameRepository.update(game);
        return game.getStatus();
    }

    @Post("/{gameId}/strategy/{role}")
    public GameStatus postStrategy(long gameId, String role, String strategy) {
        Game game = findGame(gameId);
        game.postStrategy(Game.Role.fromString(role), strategy);
        gameRepository.update(game);
        return game.getStatus();
    }

    @Get("/debug")
    public GameTEST debug() {
        TeamTEST t1 = new TeamTEST(12345L, 98, null);
        // TeamTEST t2 = new TeamTEST(12346L, 13, null);
        GameTEST g = new GameTEST(44121L, "gee", t1); //, t2);
        t1.setGame(g);
        //t2.setGame(g);
        return g;
    }

    @Get("/create-dummy")
    public Game createDummyGame() {
        Team rockies = new Team(new TeamDefinition("Colorado", "Rockies", "Ned", "Gladstone"))
                .addPlayer(new Player("Todd", "Helton", 2003, 3, 'R', 'R', 308, 999))
                .addPlayer(new Player("Larry", "Walker", 1998, 9, 'L', 'L', 297, 999));
        Team phillies = new Team(new TeamDefinition("Philadelphia", "Phillies", "Ed", "Gladstone"))
                .addPlayer(new Player("Greg", "Luzinski", 1978, 7, 'R', 'R', 276, 999))
                .addPlayer(new Player("Larry", "Bowa", 1980, 6, 'R', 'R', 266, 999));
        Game game = new Game("Sneaky little game", phillies, rockies)
                .addAction(new Action(null, null, null, 1, 0, 0, phillies.getPlayers().get(0), new Timestamp(System.currentTimeMillis()), 0, 0, "KL", "", 0, false, true)
                        .addResult(new Action(null, null, null, 0, 0, 0, phillies.getPlayers().get(1), new Timestamp(System.currentTimeMillis()), 1, 2, "PB", "", 2, false, false)));
        gameRepository.save(game);
        return game;
    }

    private Game findGame(long id) {
        Optional<Game> gameOptional = gameRepository.findById(id);
        if (gameOptional.isEmpty()) {
            throw new IllegalArgumentException("Game " + id + " does not exist");
        }
        return gameOptional.get();
    }

    private Team findTeam(long teamId) {
        return teamController.find(teamId);
    }
}
