package com.github.nedgladstone.cardball.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Game.class)
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Game {
    private static final Logger logger = LoggerFactory.getLogger("com.github.nedgladstone.cardball");

    public enum Role {
        OFFENSE,
        DEFENSE;

        public static Role fromString(String value) {
            return Role.valueOf(value.toUpperCase());
        }
    }

    public enum Side {
        VISITOR,
        HOME;

        public static Side fromString(String value) {
            return Side.valueOf(value.toUpperCase());
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_visiting_team")
    //experiment
    // @JsonManagedReference(value = "team-in-game") // NG: just uncommented
    //@JsonIgnoreProperties("game")
    private Team visitingTeam = null;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_home_team")
    // experiment
    // @JsonManagedReference(value = "team-in-game")
    //@JsonIgnoreProperties("game")
    private Team homeTeam = null;

    @OneToMany(mappedBy = "game", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    // @JsonManagedReference(value = "participant-in-team") // NGNOTE: Things were working between client and here before I took out value. I did that to troubleshoot a deser problem creating player with stats lookup
    private List<Participant> visitingLineup = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    // @JsonManagedReference(value = "participant-in-team") // NGNOTE: See above
    private List<Participant> homeLineup = new ArrayList<>();

    @Embedded
    private GameStatus status = new GameStatus();

    private String offensiveStrategy = null;
    private String defensiveStrategy = null;

    @OneToMany(mappedBy = "game", cascade = CascadeType.MERGE)
    @LazyCollection(LazyCollectionOption.FALSE)
    //@JsonManagedReference(value = "action-in-game")
    private List<Action> actions = new ArrayList<>();


    public Game(String name, Team visitingTeam, Team homeTeam) {
        this.name = name;
        this.visitingTeam = visitingTeam;
        this.homeTeam = homeTeam;
        this.status.setState(GameStatus.State.ACCEPTING_LINEUPS);
    }

    public Game addAction(Action action) {
        actions.add(action);
        action.setGame(this);
        return this;
    }

    public void putLineup(Side side, List<Participant> lineup) {
        logger.info("Game putting lineup for side " + side + ": " + lineup);
        validateLineup(lineup);
        if (status.getState() == GameStatus.State.ACCEPTING_LINEUPS) {
            // Don't treat this as a substitution, because the game has not begun
            if (side == Side.VISITOR) {
                logger.info("Lineup added as visitors");
                visitingLineup = lineup;
            } else if (side == Side.HOME) {
                logger.info("Lineup added as home");
                homeLineup = lineup;
            } else {
                logger.error("Invalid side " + side);
                throw new IllegalArgumentException("Invalid lineup side");
            }

            if ((! visitingLineup.isEmpty()) && (! homeLineup.isEmpty())) {
                changeStateToAcceptingStrategies();
            }
            return;
        }
        if (status.getState() != GameStatus.State.ACCEPTING_STRATEGIES) {
            logger.error("Attempted to add lineup when state is " + status.getState());
            throw new IllegalStateException("Not accepting lineups");
        }

        // This lineup was submitted during the game - treat it as a list of substitutions
        handleSubstitutions(side, lineup);
    }

    public void postStrategy(Role role, String strategy) {
        if (status.getState() != GameStatus.State.ACCEPTING_STRATEGIES) {
            throw new IllegalStateException("Not accepting strategies");
        }
        if (strategy == null) {
            strategy = "";
        }

        if (role == Role.OFFENSE) {
            validateOffensiveStrategy(strategy);
            offensiveStrategy = strategy;
        } else if (role == Role.DEFENSE) {
            validateDefensiveStrategy(strategy);
            defensiveStrategy = strategy;
        } else {
            throw new IllegalArgumentException("Invalid strategy role");
        }

        if ((offensiveStrategy != null) && (defensiveStrategy != null)) {
            changeStateToPlaying();
        }
    }

    private void handleSubstitutions(Side side, List<Participant> lineup) {
        logger.info("Handle substitutions");
        // TODO NG20210206 Implement
        // Loop through participants in new lineup
        //     If participant is not at same batting order slot in current lineup
        //         If participant is elsewhere in current lineup
        //             Error - not allowed to move batting order slots
        //         If participant has previously been in this game
        //             Error - not allowed to reenter game once subbed out
        //         Offensive substitution - sub out current participant by setting positions negative
        //     If participant is not at same defensive position in current lineup
        //         Defensive position change - nop?
    }

    private void validateLineup(List<Participant> lineup) {
        // TODO NG20210206 Implement
        // Ensure exactly one of each fielding position provided
        // Ensure exactly one of each batting order slot provided
        // Ensure no participants are null
    }

    private void validateOffensiveStrategy(String strategy) {
        // TODO NG20210206 Implement
        // Ensure all strategy specifiers are well-formed for offense
    }

    private void validateDefensiveStrategy(String strategy) {
        // TODO NG20210206 Implement
        // Ensure all strategy specifiers are well-formed for offense
    }

    private void changeStateToAcceptingStrategies() {
        logger.info("Changing state to accepting strategies");
        status.setState(GameStatus.State.BUSY);
        // Clear strategies
        offensiveStrategy = null;
        defensiveStrategy = null;
        status.setState(GameStatus.State.ACCEPTING_STRATEGIES);
    }

    private void changeStateToPlaying() {
        status.setState(GameStatus.State.BUSY);
        play();
        status.setState(GameStatus.State.PLAYING);
    }

    private void play() {
        // Evaluate offensive strategy
    }

}
