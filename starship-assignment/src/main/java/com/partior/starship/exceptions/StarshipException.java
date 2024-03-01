package com.partior.starship.exceptions;

public class StarshipException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StarshipException(String s) {
		super(s);
	}

	public StarshipException(String s, Exception e) {
		super(s, e);
	}

}
