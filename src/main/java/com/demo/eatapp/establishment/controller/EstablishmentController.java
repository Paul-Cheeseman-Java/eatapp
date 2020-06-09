package com.demo.eatapp.establishment.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@RestController
public class EstablishmentController {

	@Autowired
	private EstablishmentDAO establishmentDAO;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@GetMapping("/add")
	public void index(RestTemplate restTemplate) {
		
		HttpHeaders headers = new HttpHeaders();
		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("x-api-version", "2");
		// build the request
		HttpEntity request = new HttpEntity(headers);
		
		//Hardcoded URL for testing
		String url = "https://api.ratings.food.gov.uk/Establishments?address=gu51 3ps";
		
		// make an HTTP GET request with headers
		ResponseEntity<Establishments> response = restTemplate.exchange(
		        url,
		        HttpMethod.GET,
		        request,
		        Establishments.class
		);
		//Maybe filter out anything but cafes, bars, restaurants - ie places you can sit down to eat/drink
		for (Establishment est : response.getBody().getEstablishments()) {
			System.out.println("----- Adding to List ----------------");
			System.out.println("Establishment id: " + est.getFhrsID());
			System.out.println("Establishment name: " + est.getName());
			establishmentDAO.addToList(est, "test");
			System.out.println("-   -   -   -   -   -   -  -");
		}
		
	}

	
	@GetMapping("/list")
	public void getList() {

		List<Establishment> listOfEstablishment = establishmentDAO.getList("test");
		//Maybe filter out anything but cafes, bars, restaurants - ie places you can sit down to eat/drink
		int val = 0;
		for (Establishment est : listOfEstablishment) {
			val++;
			System.out.println("----- List Ouput #" + val + " ----------------");
			System.out.println("Establishment id: " + est.getFhrsID());
			System.out.println("Establishment name: " + est.getName());
			establishmentDAO.addToList(est, "test");
			System.out.println("-   -   -   -   -   -   -  -");
		}
		
	}
	
	

}
