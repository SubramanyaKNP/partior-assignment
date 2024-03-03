package com.partior.starship.controller;

import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.partior.starship.dao.StarshipAPIResponse;
import com.partior.starship.services.StarshipService;

@RestController
public class StarshipController {

	private Logger logger = LogManager.getLogger(StarshipController.class);

	@Autowired
	private StarshipService starshipService;

	@GetMapping("/information/{id}")
	public ResponseEntity<StarshipAPIResponse> getStarshipInfo(@PathVariable("id") String id) {
		StarshipAPIResponse sr = starshipService.fetchInformation(id);
		logger.info("Reponse is {}", sr.getStarship());
		return ResponseEntity.ok(sr);
	}

	@GetMapping("/async/information/{id}")
	public CompletableFuture<ResponseEntity<StarshipAPIResponse>> getAsyncStarshipInfo(@PathVariable("id") String id) {
		CompletableFuture<StarshipAPIResponse> future = starshipService.pollStarWarsInformationAsync(id);
		sleep();
		logger.info("Post first sleep");
		return future.thenApply(ResponseEntity::ok);
	}

	private void sleep() {
		logger.info("Inside sleep method......");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
