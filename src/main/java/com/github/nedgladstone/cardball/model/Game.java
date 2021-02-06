package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_visiting_team", nullable = false)
    private Team visitingTeam;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_home_team", nullable = false)
    private Team homeTeam;

    @Column(name = "score_visitors")
    private int scoreVisitors;

    @Column(name = "score_home")
    private int scoreHome;

    @Column(name = "inning")
    private int inning;

    @Column(name = "half")
    private int half;

    @Column(name = "batter")
    private int batter;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Action> actions;

    public Game() {
    }

    public Game(@NotNull Team visitingTeam, @NotNull Team homeTeam, int scoreVisitors, int scoreHome, int inning, int half, int batter, List<Action> actions) {
        this.visitingTeam = visitingTeam;
        this.homeTeam = homeTeam;
        this.scoreVisitors = scoreVisitors;
        this.scoreHome = scoreHome;
        this.inning = inning;
        this.half = half;
        this.batter = batter;
        this.actions = (actions != null) ? actions : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(Team visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getScoreVisitors() {
        return scoreVisitors;
    }

    public void setScoreVisitors(int scoreVisitors) {
        this.scoreVisitors = scoreVisitors;
    }

    public int getScoreHome() {
        return scoreHome;
    }

    public void setScoreHome(int scoreHome) {
        this.scoreHome = scoreHome;
    }

    public int getInning() {
        return inning;
    }

    public void setInning(int inning) {
        this.inning = inning;
    }

    public int getHalf() {
        return half;
    }

    public void setHalf(int half) {
        this.half = half;
    }

    public int getBatter() {
        return batter;
    }

    public void setBatter(int batter) {
        this.batter = batter;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setAction(List<Action> actions) {
        this.actions = actions;
    }

    public Game addAction(Action action) {
        actions.add(action);
        action.setGame(this);
        return this;
    }
}
