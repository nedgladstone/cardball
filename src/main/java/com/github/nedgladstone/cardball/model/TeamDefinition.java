package com.github.nedgladstone.cardball.model;

import lombok.*;

import javax.persistence.Embeddable;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Embeddable
public class TeamDefinition {
    private String location;

    private String name;

    private String managerFirstName;

    private String managerLastName;

}
