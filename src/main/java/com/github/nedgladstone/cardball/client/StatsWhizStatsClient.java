package com.github.nedgladstone.cardball.client;

import com.github.nedgladstone.cardball.model.StatsWhizStats;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.List;

@Client("http://localhost:8081/stats")
public interface StatsWhizStatsClient {
    @Get("/{id}")
    StatsWhizStats getStats(long id);

    @Get("/find")
    StatsWhizStats findStats(@QueryValue long playerId, @QueryValue int year);
}
