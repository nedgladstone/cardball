package com.github.nedgladstone.cardball.client;

import com.github.nedgladstone.cardball.model.StatsWhizPlayer;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

@Client("http://localhost:8081/player")
public interface StatsWhizPlayerClient {
    @Get("/list")
    Iterable<StatsWhizPlayer> list();

    @Get("/{id}")
    StatsWhizPlayer getPlayer(long id);

    @Get("/find")
    StatsWhizPlayer findPlayer(@QueryValue String lastName, @QueryValue String firstName);

    @Get("/test")
    String test(String testParam);
}
