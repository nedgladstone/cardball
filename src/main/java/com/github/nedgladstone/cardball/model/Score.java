package com.github.nedgladstone.cardball.model;

import lombok.*;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Score {
    private int runs = 0;
    private int hits = 0;
    private int errors = 0;
}
