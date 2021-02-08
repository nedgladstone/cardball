package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatsWhizPlayer.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class StatsWhizStats {
    private Long id;

    // @JsonBackReference
    private Player player;

    private int season;

    private String sport;

    private String league;

    private String team;

    private Integer gamesPlayed;

    private Integer gamesPitched;

    private Integer numPitchesSeen;

    private Integer numPitchesThrown;

    // Batting

    private Integer ab;

    private Double avg;

    private Integer hr;

    private Integer rbi;

    // Baserunning

    private Integer cs;

    private Integer sb;

    // Pitching

    private Double pitchAvg;

    private Double pitchEra;
}
