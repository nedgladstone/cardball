package com.github.nedgladstone.cardball.repository;

import com.github.nedgladstone.cardball.model.Game;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
}
