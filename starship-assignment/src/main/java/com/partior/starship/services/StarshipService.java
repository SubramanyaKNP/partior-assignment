package com.partior.starship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.partior.starship.dao.Starship;
import com.partior.starship.dao.StarshipAPIResponse;
import com.partior.starship.exceptions.StarshipException;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * Service class to fetch starship information from SWAPI (Star Wars API).
 *
 * @author subramanya
 */
@Service
public class StarshipService {

	private static final Logger LOGGER = LogManager.getLogger(StarshipService.class);

	private final RequestSpecification rs;
	private final RequestSpecification rsWithoutBaseUri;

	String swapiUrl;

	@Value("${starship.owner}")
	private String starShipOwner;

	@Value("${planet}")
	private String planetName;

	/**
	 * Constructs a new StarshipService with the provided SWAPI URL and starship
	 * owner.
	 *
	 * @param swapiUrl      the URL of SWAPI
	 * @param starShipOwner the owner of the starships to fetch
	 * @author subramanya
	 */
	public StarshipService(@Value("${swapi.url}") String swapiUrl) {
		this.swapiUrl = swapiUrl;
		this.rs = new RequestSpecBuilder().setProxy("http://www-proxy.us.oracle.com:80").setBaseUri(swapiUrl)
				.setRelaxedHTTPSValidation().build();
		this.rsWithoutBaseUri = new RequestSpecBuilder().setProxy("http://www-proxy.us.oracle.com:80")
				.setRelaxedHTTPSValidation().build();
	}

	/**
	 * Fetches information about a starship with the given ID.
	 *
	 * @param id the ID of the starship to fetch
	 * @return the starship information
	 * @throws StarshipException if an error occurs while fetching starship
	 *                           information
	 * @author subramanya
	 */
	public StarshipAPIResponse fetchInformation(final String id) {
		LOGGER.info("Fetching starship information...");
		Response response = RestAssured.given(rs).get("/starships/{id}/", id);
		if (response.getStatusCode() != HttpStatus.OK.value()) {
			throw new StarshipException("Failed to fetch information from SWAPI");
		}

		StarshipAPIResponse starshipAPIResponse = new StarshipAPIResponse();
		try {
			List<String> starships = findStarshipsForOwner(starShipOwner);
			starships.forEach(starshipUrl -> {
				Starship starship = getStarshipDetails(starshipUrl);
				starshipAPIResponse.setStarship(starship);
			});
			starshipAPIResponse.setCrew(findCrews("Death Star"));
		} catch (Exception e) {
			LOGGER.error("Error occurred while fetching starship information", e);
			throw new StarshipException("Error occurred while fetching starship information", e);
		}
		starshipAPIResponse.setLeiaOnPlanet(isLeiaOnPlanet());
		return starshipAPIResponse;
	}

	public StarshipAPIResponse fetchInformationAsync(final String id) {
		// Simulating async operation
		try {
			LOGGER.info("Explicit deplay of 5 seconds .....");
			Thread.sleep(5000); // Simulating a delay of 5 seconds
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return fetchInformation(id);
	}

	private boolean isLeiaOnPlanet() {
		Response response = RestAssured.given(rs).get("/planets");
		List<String> planetsList = response.jsonPath().getList("results.name");
		List<String> residentsUrlList = null;
		for (String planet : planetsList) {
			if (planet.equals(planetName)) {
				residentsUrlList = response.jsonPath().getList("results.find{it.name=='" + planetName + "'}.residents");
				break;
			}
		}
		return (residentsUrlList != null)
				&& residentsUrlList.stream().map(resident -> RestAssured.given(rsWithoutBaseUri).get(resident))
						.anyMatch(x -> x.jsonPath().getString("name").contains("Leia"));
	}

	/**
	 * Fetches details of a starship from SWAPI using the given URL.
	 *
	 * @param starshipUrl the URL of the starship details
	 * @return the starship details
	 * @throws StarshipException if an error occurs while fetching starship details
	 * @author subramanya
	 */
	private Starship getStarshipDetails(String starshipUrl) {
		LOGGER.info("Fetching starship details...");
		Response response = RestAssured.given(rsWithoutBaseUri).get(starshipUrl);
		if (response.getStatusCode() != HttpStatus.OK.value()) {
			throw new StarshipException("Failed to fetch starship details from SWAPI");
		}

		Starship starship = new Starship();
		starship.setName(response.body().jsonPath().getString("name"));
		starship.setStarShipClass(response.body().jsonPath().getString("starship_class"));
		starship.setModel(response.body().jsonPath().getString("model"));
		return starship;
	}

	/**
	 * Finds the crew count for the starship with the given name.
	 *
	 * @param starshipName the name of the starship
	 * @return the crew count of the starship
	 * @throws StarshipException if an error occurs while fetching crew count
	 * @author subramanya
	 */
	private Integer findCrews(String starshipName) {
		LOGGER.info("Fetching crew count for starship...");
		Response response = RestAssured.given(rs).get("/starships");
		if (response.getStatusCode() != HttpStatus.OK.value()) {
			throw new StarshipException("Failed to fetch starship list from SWAPI");
		}
		List<String> starshipNames = response.jsonPath().getList("results.name");
		int crewCount = 0;
		for (String name : starshipNames) {
			if (name.equals(starshipName)) {
				String crewString = response.jsonPath()
						.getString("results.find{it.name == '" + starshipName + "'}.crew");
				crewCount = Integer.parseInt(crewString.replaceAll("[^\\d]", ""));
				LOGGER.info("Crew count for starship {} is {}", starshipName, crewCount);
				break;
			}
		}
		return crewCount;
	}

	/**
	 * Finds the starships associated with the starship owner.
	 *
	 * @return the list of starship URLs associated with the owner
	 * @throws StarshipException if an error occurs while fetching starships
	 * @author subramanya
	 */
	private List<String> findStarshipsForOwner(String starShipOwner) {
		LOGGER.info("Fetching starships for owner...");
		Response response = RestAssured.given(rs).get("/people/");
		if (response.getStatusCode() != HttpStatus.OK.value()) {
			throw new StarshipException("Failed to fetch people list from SWAPI");
		}
		List<String> starshipUrls = response.jsonPath()
				.getList("results.find{it.name == '" + starShipOwner + "'}.starships");
		List<String> starships = new ArrayList<>();
		for (String starshipUrl : starshipUrls) {
			String starshipName = RestAssured.given(rsWithoutBaseUri).get(starshipUrl).body().jsonPath()
					.getString("name");
			LOGGER.info("Found starship for owner {}: {}", starShipOwner, starshipName);
			starships.add(starshipUrl);
		}
		return starships;
	}
}