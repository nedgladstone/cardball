package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Embedded
    private TeamDefinition definition;

    @Column(name = "wins_this_season")
    private int winsThisSeason = 0;

    @Column(name = "losses_this_season")
    private int lossesThisSeason = 0;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    public Team(TeamDefinition definition) {
        this.definition = definition;
    }

    public Team addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
        return this;
    }
}
