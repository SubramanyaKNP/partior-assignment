package com.partior.starship.dao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StarshipAPIResponse {
	private Starship starship;
	private Integer crew;
	private boolean isLeiaOnPlanet;
}
