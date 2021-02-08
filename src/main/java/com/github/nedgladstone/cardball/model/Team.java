package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
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

    @OneToMany(mappedBy = "team", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    //@JsonManagedReference(value = "player-in-team")
    private List<Player> players = new ArrayList<>();

    // experiment
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_game")
    // experiment
    //@JsonBackReference(value = "team-in-game")
    //@JsonManagedReference(value = "team-in-game")
    //@JsonIgnoreProperties({"visitingTeam", "homeTeam"})
    private Game game;

    public Team(TeamDefinition definition) {
        this.definition = definition;
    }

    public Team addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
        return this;
    }
}
