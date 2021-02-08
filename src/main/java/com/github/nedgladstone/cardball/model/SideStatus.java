package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Embeddable
public class SideStatus {
    private int runs = 0;

    private int hits = 0;

    private int errors = 0;

    private int outs = 0;

    private int balls = 0;

    private int strikes = 0;

    // The following fields are set to the slot number in the batting order
    // Or 0 if unoccupied
    private int batter = 0;

    private int onFirst = 0;

    private int onSecond = 0;

    private int onThird = 0;
}
