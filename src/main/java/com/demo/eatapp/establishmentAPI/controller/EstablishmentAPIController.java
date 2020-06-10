package com.demo.eatapp.establishmentAPI.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;

@RestController
public class EstablishmentAPIController {

	/*
	@Autowired
	private EstablishmentDAO establishmentDAO;
	

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
	
	@GetMapping("/establistments")
	public ResponseEntity<Object> getUsers(){

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/list")
				.build().toUri();
		
		return ResponseEntity.created(location).build();
	}
	*/
}
