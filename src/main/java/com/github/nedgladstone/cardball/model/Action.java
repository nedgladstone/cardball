package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "cause")
    @JsonBackReference
    private Action cause;

    @OneToMany(mappedBy = "cause", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Action> results;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_game")
    @JsonBackReference
    private Game game;

    @Column(name = "inning")
    private int inning;

    @Column(name = "half")
    private int half;

    @Column(name = "batter")
    private int batter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_player")
    private Player player;

    @Column(name = "time")
    private Timestamp time;

    @Column(name = "from_base")
    private int fromBase;

    @Column(name = "to_base")
    private int toBase;

    // Batter outcomes
    //KL: Strikeout looking
    //KS: Strikeout swinging
    //GO: Ground out
    //LO: Line out
    //FO: Fly out
    //PO: Pop out
    //BB: Base on balls
    //HB: Hit batsman
    //CI: Reached on catcher's interference
    //RE: Reached on error
    //RK: Reached on dropped third strike
    //FC: Hit into fielder's choice
    //SC: Sacrifice fly or bunt
    //HI: Hit
    //GR: Ground rule hit

    // Runner outcomes
    //PK: Picked off
    //CS: Caught stealing
    //SB: Stolen base
    //AO: Advanced on out
    //AE: Advanced on error
    //AB: Advanced on balk
    //AW: Advanced on wild pitch
    //AP: Advanced on passed ball
    //AH: Advanced on hit
    @Column(name = "outcome")
    private String outcome;

    @Column(name = "fielder_touches")
    private String fielderTouches;

    @Column(name = "error_on_fielder")
    private int errorOnFielder;

    @Column(name = "is_run")
    private boolean isRun;

    @Column(name = "is_out")
    private boolean isOut;

    public Action() {
    }

    public Action(Action cause, List<Action> results, Game game, int inning, int half, int batter, Player player, Timestamp time, int fromBase, int toBase, String outcome, String fielderTouches, int errorOnFielder, boolean isRun, boolean isOut) {
        this.cause = cause;
        this.results = (results != null) ? results : new ArrayList<>();
        this.game = game;
        this.inning = inning;
        this.half = half;
        this.batter = batter;
        this.player = player;
        this.time = time;
        this.fromBase = fromBase;
        this.toBase = toBase;
        this.outcome = outcome;
        this.fielderTouches = fielderTouches;
        this.errorOnFielder = errorOnFielder;
        this.isRun = isRun;
        this.isOut = isOut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Action getCause() {
        return cause;
    }

    public void setCause(Action cause) {
        this.cause = cause;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getFromBase() {
        return fromBase;
    }

    public void setFromBase(int fromBase) {
        this.fromBase = fromBase;
    }

    public int getToBase() {
        return toBase;
    }

    public void setToBase(int toBase) {
        this.toBase = toBase;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getFielderTouches() {
        return fielderTouches;
    }

    public void setFielderTouches(String fielderTouches) {
        this.fielderTouches = fielderTouches;
    }

    public int getErrorOnFielder() {
        return errorOnFielder;
    }

    public void setErrorOnFielder(int errorOnFielder) {
        this.errorOnFielder = errorOnFielder;
    }

    public boolean isRun() {
        return isRun;
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    public boolean isOut() {
        return isOut;
    }

    public void setOut(boolean out) {
        isOut = out;
    }

    public Action addResult(Action result) {
        results.add(result);
        result.setCause(this);
        return this;
    }
}
