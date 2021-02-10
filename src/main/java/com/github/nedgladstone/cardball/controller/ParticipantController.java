package com.github.nedgladstone.cardball.controller;

import com.github.nedgladstone.cardball.model.Game;
import com.github.nedgladstone.cardball.model.Participant;
import com.github.nedgladstone.cardball.model.ParticipantDefinition;
import com.github.nedgladstone.cardball.repository.ParticipantRepository;
import com.github.nedgladstone.cardball.repository.PlayerRepository;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExecuteOn(TaskExecutors.IO)
@Controller("/participant")
public class ParticipantController {
    private static final Logger logger = LoggerFactory.getLogger("com.github.nedgladstone.cardball");

    protected final ParticipantRepository participantRepository;
    protected final PlayerController playerController;

    public ParticipantController(ParticipantRepository participantRepository, PlayerController playerController) {
        this.participantRepository = participantRepository;
        this.playerController = playerController;
    }

    @Get
    public Iterable<Participant> list() {
        return participantRepository.findAll();
    }

    public Participant create(Game game, ParticipantDefinition participantDef) {
        Participant participant = new Participant(
                game,
                participantDef.getBattingOrderSlot(),
                participantDef.getFieldingPosition(),
                playerController.find(participantDef.getPlayerId()));
        participantRepository.save(participant);
        logger.info("Created participant " + participant.getPlayer().getLastName() + " with id " + participant.getId());
        return participant;
    }
}
