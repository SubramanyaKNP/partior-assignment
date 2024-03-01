package com.partior.starship.dao;

import lombok.Data;

@Data
public class StarshipAPIResponse {
	private Starship starship;
    private Integer crew;
    private boolean isLeiaOnPlanet;
}
