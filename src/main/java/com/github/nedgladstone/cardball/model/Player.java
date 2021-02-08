package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "year")
    private int year;

    @Column(name = "position")
    private int position;

    // R: right, L: left, r: right/switch, l: left/switch, A: ambidextrous
    @Column(name = "batting_hand")
    private char battingHand;

    // R: right, L: left, r: right/switch, l: left/switch, A: ambidextrous
    @Column(name = "throwing_hand")
    private char throwingHand;

    @Column(name = "batting_average")
    private int battingAverage;

    @Column(name = "era")
    private int era;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_team")
    //@JsonBackReference(value = "player-in-team")
    private Team team;

    public Player(String firstName, String lastName, int year, int position, char battingHand, char throwingHand, int battingAverage, int era) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.position = position;
        this.battingHand = battingHand;
        this.throwingHand = throwingHand;
        this.battingAverage = battingAverage;
        this.era = era;
        this.team = null;
    }
}
