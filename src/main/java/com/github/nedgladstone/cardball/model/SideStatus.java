package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class SideStatus {
    private Score score = new Score();
    private int batter = 0;

    // index 0 is batter; 1-3 are runners on corresponding bases
    @JsonIdentityReference(alwaysAsId = true)
    private List<Player> bases = new ArrayList<>();
}
