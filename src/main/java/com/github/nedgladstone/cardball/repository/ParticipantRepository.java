package com.github.nedgladstone.cardball.repository;

import com.github.nedgladstone.cardball.model.Participant;
import com.github.nedgladstone.cardball.model.Player;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ParticipantRepository extends CrudRepository<Participant, Long> {
}
