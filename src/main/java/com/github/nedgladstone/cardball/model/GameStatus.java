package com.github.nedgladstone.cardball.model;

import io.micronaut.core.annotation.Introspected;
import lombok.*;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class GameStatus {
    public enum State {
        ACCEPTING_LINEUPS,
        ACCEPTING_STRATEGIES,
        BUSY,
        PLAYING,
        GAME_OVER
    }

    @Enumerated(EnumType.ORDINAL)
    private State state = State.ACCEPTING_LINEUPS;

    private int inning = 1;

    private int half = 0;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "runs", column = @Column(name = "visitor_runs")),
            @AttributeOverride(name = "hits", column = @Column(name = "visitor_hits")),
            @AttributeOverride(name = "errors", column = @Column(name = "visitor_errors")),
            @AttributeOverride(name = "batter", column = @Column(name = "visitor_batter")),
            @AttributeOverride(name = "onFirst", column = @Column(name = "visitor_on_first")),
            @AttributeOverride(name = "onSecond", column = @Column(name = "visitor_on_second")),
            @AttributeOverride(name = "onThird", column = @Column(name = "visitor_on_third"))
    })
    private SideStatus visitorStatus = new SideStatus();

    @Embedded
    private SideStatus homeStatus = new SideStatus();
}
