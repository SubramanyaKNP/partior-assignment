package com.partior.starship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.partior.starship.dao.Starship;
import com.partior.starship.services.StarshipService;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

/**
 * Unit test for simple App.
 */
public class StarshipTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	@Spy
	private StarshipService starshipService = new StarshipService("https://swapi.dev/api");

	@Value("${swapi.url}")
	private String swapiUrl;

	@Mock
	private RestAssured restAssuredMock;

	@BeforeMethod
	public void setupMockito() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFetchInformation() {
		String id = "123";
		String starshipUrl = "https://swapi.dev/api/starships/123/";
		Starship starship = new Starship();
		starship.setName("Millennium Falcon");
		starship.setStarShipClass("YT-1300 light freighter");
		starship.setModel("YT-1300f");

		Map<String, Object> starshipDetails = new HashMap<>();
		starshipDetails.put("name", starship.getName());
		starshipDetails.put("starship_class", starship.getStarShipClass());
		starshipDetails.put("model", starship.getModel());

		Map<String, Object> personDetails = new HashMap<>();
		personDetails.put("starships", Collections.singletonList(starshipUrl));

		List<Map<String, Object>> planetsList = new ArrayList<>();
		Map<String, Object> planet = new HashMap<>();
		planet.put("name", "");
		planet.put("residents", Collections.singletonList("https://swapi.dev/api/people/5/"));
		planetsList.add(planet);
		RequestSpecification rsMock = Mockito.mock(RequestSpecification.class);
		RequestSpecification rsWithoutBaseUriMock = Mockito.mock(RequestSpecification.class);
		Mockito.when(restAssuredMock.given(rsMock).get("/starships/{id}/", id))
				.thenReturn(response(HttpStatus.OK.value(), starshipDetails));
		Mockito.when(restAssuredMock.given(rsMock).get("/people/"))
				.thenReturn(response(HttpStatus.OK.value(), Collections.singletonList(personDetails)));
		Mockito.when(restAssuredMock.given(rsMock).get("/planets"))
				.thenReturn(response(HttpStatus.OK.value(), planetsList));
		Mockito.when(restAssuredMock.given(rsWithoutBaseUriMock).get(starshipUrl))
				.thenReturn(response(HttpStatus.OK.value(), starshipDetails));
		Mockito.when(restAssuredMock.given(rsWithoutBaseUriMock).get("https://swapi.dev/api/people/5/"))
				.thenReturn(response(HttpStatus.OK.value(), Collections.singletonMap("name", "Leia Organa")));
//		Starship ss = Starship.builder().model("ABC").name("XYZ").starShipClass("QWERTY").build();
//		StarshipAPIResponse starshipAPIResponse = StarshipAPIResponse.builder().starship(ss).isLeiaOnPlanet(false)
//				.crew(12).build();
//		Mockito.when(restTemplate.getForEntity("/information", StarshipAPIResponse.class, "9"))
//				.thenReturn(new ResponseEntity<StarshipAPIResponse>(starshipAPIResponse, HttpStatus.OK));
//		StarshipAPIResponse ssapi = starshipService.fetchInformation(Mockito.anyString());
//		Assert.assertEquals(ssapi, starshipAPIResponse);
	}

	private Response response(int statusCode, Object body) {
		Response response = Mockito.mock(Response.class);
		Mockito.when(response.getStatusCode()).thenReturn(statusCode);
		Mockito.when(response.body()).thenReturn((ResponseBody) body);
		return response;
	}

}
