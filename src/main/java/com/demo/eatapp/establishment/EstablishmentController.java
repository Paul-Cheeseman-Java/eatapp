package com.demo.eatapp.establishment;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

@RestController
public class EstablishmentController {

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
		ResponseEntity<EstablishmentsModel> response = restTemplate.exchange(
		        url,
		        HttpMethod.GET,
		        request,
		        EstablishmentsModel.class
		);
		
		for (EstablishmentModel est : response.getBody().getEstablishments()) {
			System.out.println("---------------------------");
			System.out.println("Class name: " + est.getClass());
			System.out.println("Business id: " + est.getId());
			System.out.println("Business name: " + est.getBusinessName());
			System.out.println("Business type id: " + est.getBusinessTypeID());
			System.out.println("Business postcode: " + est.getPostcode());
		}
		
	}

	

}
