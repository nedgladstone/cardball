package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "location")
    private String location;

    @Column(name = "name")
    private String name;

    @Column(name = "manager_first_name")
    private String managerFirstName;

    @Column(name = "manager_last_name")
    private String managerLastName;

    @Column(name = "wins_this_season")
    private int winsThisSeason;

    @Column(name = "losses_this_season")
    private int lossesThisSeason;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Player> players;

    public Team() {
    }

    public Team(String location, String name, String managerFirstName, String managerLastName, int winsThisSeason, int lossesThisSeason, List<Player> players) {
        this.location = location;
        this.name = name;
        this.managerFirstName = managerFirstName;
        this.managerLastName = managerLastName;
        this.winsThisSeason = winsThisSeason;
        this.lossesThisSeason = lossesThisSeason;
        this.players = (players != null) ? players : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerFirstName() {
        return managerFirstName;
    }

    public void setManagerFirstName(String managerFirstName) {
        this.managerFirstName = managerFirstName;
    }

    public String getManagerLastName() {
        return managerLastName;
    }

    public void setManagerLastName(String managerLastName) {
        this.managerLastName = managerLastName;
    }

    public int getWinsThisSeason() {
        return winsThisSeason;
    }

    public void setWinsThisSeason(int winsThisSeason) {
        this.winsThisSeason = winsThisSeason;
    }

    public int getLossesThisSeason() {
        return lossesThisSeason;
    }

    public void setLossesThisSeason(int lossesThisSeason) {
        this.lossesThisSeason = lossesThisSeason;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Team addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
        return this;
    }
}
