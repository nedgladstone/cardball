package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.Game;
import com.github.nedgladstone.cardball.model.Team;
import com.github.nedgladstone.cardball.model.TeamDefinition;
import com.github.nedgladstone.cardball.repository.TeamRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.Optional;

@ExecuteOn(TaskExecutors.IO)
@Controller("/team")
public class TeamController {
    protected final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Get
    public Iterable<Team> list() {
        return teamRepository.findAll();
    }

    @Get("/{id}")
    public Team find(long id) {
        return findTeam(id);
    }

    @Post
    public void create(TeamDefinition definition) {
        Team team = new Team(definition);
        teamRepository.save(team);
    }

    private Team findTeam(long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        if (teamOptional.isEmpty()) {
            throw new IllegalArgumentException("Team " + id + " does not exist");
        }
        return teamOptional.get();
    }
}
