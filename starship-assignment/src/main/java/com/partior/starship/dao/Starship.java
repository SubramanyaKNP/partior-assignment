package com.partior.starship.dao;

import java.util.List;

import lombok.Data;

@Data
public class Starship {
	private String name;
	private String model;
	private List<String> pilots;
	private List<String> films;
	private String starShipClass;
}
