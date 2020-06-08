package com.demo.eatapp.establishment.controller;

import java.util.Collections;

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

	@GetMapping("/test")
	public void index(RestTemplate restTemplate) {
	
		
		HttpHeaders headers = new HttpHeaders();
		//https://attacomsian.com/blog/spring-boot-resttemplate-get-request-parameters-headers
		// set `Content-Type` and `Accept` headers
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("x-api-version", "2");
		// build the request
		HttpEntity request = new HttpEntity(headers);
		
		System.out.println("Rest API hit");
		// request url
		//String url = "https://api.ratings.food.gov.uk/Establishments/909658";
		
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
			System.out.println("---------------------------");
			
			est.setPhone("12345 67890");
			
			System.out.println("Class name: " + est.getClass());
			System.out.println("Establishment id: " + est.getFhrsID());
			System.out.println("Establishment name: " + est.getName());
			System.out.println("Establishment type: " + est.getType());
			System.out.println("Establishment type id: " + est.getTypeID());
			System.out.println("Address line 1: " + est.getAddressLine1());
			System.out.println("Address line 2: " + est.getAddressLine2());
			System.out.println("Address line 3: " + est.getAddressLine3());
			System.out.println("Address line 4: " + est.getAddressLine4());
			System.out.println("Postcode: " + est.getPostcode());
			System.out.println("Phone: " + est.getPhone());
			System.out.println("Rating Value: " + est.getRatingValue());
			System.out.println("Rating Date: " + est.getRatingDate());
			System.out.println("-   -   -   -   -   -   -  -");
			System.out.println("Saving est-id: " + est.getFhrsID());
			establishmentDAO.addToList(est, "test");
			System.out.println("-   -   -   -   -   -   -  -");
		}
		
	}

	

}
