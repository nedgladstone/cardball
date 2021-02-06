package com.github.nedgladstone.cardball.model;

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
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

    private SideStatus visitorStatus = new SideStatus();

    private SideStatus homeStatus = new SideStatus();
}
