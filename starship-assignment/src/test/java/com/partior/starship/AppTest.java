package com.partior.starship;

import java.util.List;

import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		RequestSpecification rs = new RequestSpecBuilder().setBaseUri("https://swapi.dev/api")
				.setRelaxedHTTPSValidation().build();
		Response response = RestAssured.given(rs).get("https://swapi.dev/api/planets");
		List<String> planetsList = response.jsonPath().getList("results.name");
		for (String planet : planetsList) {
			if (planet.equals("Alderaan")) {
				List<String> l = response.jsonPath().getList("results.find{it.name=='" + "Alderaan" + "'}.residents");
				System.out.println("Residents are " + l);
			}
		}
		System.out.println(planetsList);
		if (response.getStatusCode() != HttpStatus.OK.value()) {
			throw new RuntimeException("Failed to fetch information from SWAPI");
		}
		System.out.println("Response is OK.....");
	}

}
