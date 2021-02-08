package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_game")
    @JsonBackReference(value = "participant-in-game")
    private Game game;

    // -1 - -9 = batting order slot before being substituted out
    // 0 = not in the batting order (has not been in game, or is pitcher with DH)
    // 1 - 9 = batting order slot with 1 batting first
    @Column(name = "batting_order_slot")
    private int battingOrderSlot;

    // -1 - -9 = fielding position before being substituted out
    // 0 = not fielding (has not been in game, or is the DH)
    // 1 - 9 = fielding position per scorecard notation
    @Column(name = "fielding_position")
    private int fieldingPosition;

    //@JsonIdentityReference(alwaysAsId = true)
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_player")
    private Player player;

    public Participant(Game game, int battingOrderSlot, int fieldingPosition, Player player) {
        //this.game = game;
        this.battingOrderSlot = battingOrderSlot;
        this.fieldingPosition = fieldingPosition;
        this.player = player;
    }
}
