package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.Participant;
import com.github.nedgladstone.cardball.repository.ParticipantRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

@ExecuteOn(TaskExecutors.IO)
@Controller("/participant")
public class ParticipantController {
    protected final ParticipantRepository participantRepository;

    public ParticipantController(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Get("/list")
    public Iterable<Participant> list() {
        return participantRepository.findAll();
    }
}
