package com.github.nedgladstone.cardball.repository;

import com.github.nedgladstone.cardball.model.Team;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {
}
