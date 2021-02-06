package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.Team;
import com.github.nedgladstone.cardball.repository.TeamRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.IO)
@Controller("/team")
public class TeamController {
    protected final TeamRepository teamRepository;

    public TeamController(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Get("/list")
    public Iterable<Team> list() {
        return teamRepository.findAll();
    }
}
