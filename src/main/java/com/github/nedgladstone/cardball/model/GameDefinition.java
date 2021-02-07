package com.github.nedgladstone.cardball.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class GameDefinition {
    private String name;
    private long visitingTeamId;
    private long homeTeamId;
}
