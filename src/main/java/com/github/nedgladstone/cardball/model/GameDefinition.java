package com.github.nedgladstone.cardball.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class GameDefinition {
    private long visitingTeamId;
    private long homeTeamId;
}
