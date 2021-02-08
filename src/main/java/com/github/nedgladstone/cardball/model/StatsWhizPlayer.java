package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = StatsWhizPlayer.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class StatsWhizPlayer {
    private Long id;

    private Long mlbPlayerId;

    private String nameLast;

    private String nameFirst;

    private LocalDate birthDate;

    private String birthCountry;

    private String college;

    private int heightInches;

    private int weightPounds;

    private LocalDate proDebutDate;

    private char battingHand;

    private char throwingHand;

    private int position;

    // @JsonManagedReference
    private List<StatsWhizStats> stats = new ArrayList<>();
}
