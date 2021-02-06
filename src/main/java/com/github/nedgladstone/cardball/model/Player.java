package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_team")
    @JsonBackReference
    private Team team;

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

    public Player() {
    }

    public Player(String firstName, String lastName, int year, int position, Team team, char battingHand, char throwingHand, int battingAverage, int era) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.position = position;
        this.team = team;
        this.battingHand = battingHand;
        this.throwingHand = throwingHand;
        this.battingAverage = battingAverage;
        this.era = era;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public char getBattingHand() {
        return battingHand;
    }

    public void setBattingHand(char battingHand) {
        this.battingHand = battingHand;
    }

    public char getThrowingHand() {
        return throwingHand;
    }

    public void setThrowingHand(char throwingHand) {
        this.throwingHand = throwingHand;
    }

    public int getBattingAverage() {
        return battingAverage;
    }

    public void setBattingAverage(int battingAverage) {
        this.battingAverage = battingAverage;
    }

    public int getEra() {
        return era;
    }

    public void setEra(int era) {
        this.era = era;
    }
}
