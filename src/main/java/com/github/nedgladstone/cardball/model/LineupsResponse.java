package com.github.nedgladstone.cardball.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class LineupsResponse {
    private List<Participant> visitingLineup;
    private List<Participant> homeLineup;
}
