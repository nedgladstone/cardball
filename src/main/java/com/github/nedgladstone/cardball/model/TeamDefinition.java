package com.github.nedgladstone.cardball.model;

import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class TeamDefinition {
    private String location;

    private String name;

    private String managerFirstName;

    private String managerLastName;

}
