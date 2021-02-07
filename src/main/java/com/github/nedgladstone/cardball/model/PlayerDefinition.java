package com.github.nedgladstone.cardball.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Setter @Getter
public class PlayerDefinition {
    private String lastName;
    private String firstName;
    private long mlbPlayerId;
    private int year;
}
