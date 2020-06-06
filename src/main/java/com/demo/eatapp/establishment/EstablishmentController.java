package com.demo.eatapp.establishment;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
		// set `Content-Type` and `Accept` headers
		//headers.setContentType(MediaType.APPLICATION_JSON);
		//headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("x-api-version", "2");
		// build the request
		HttpEntity request = new HttpEntity(headers);
		
		
		System.out.println("Rest API hit");
		
		// request url
		String url = "https://api.ratings.food.gov.uk/Establishments/909658";
		
		//EstablishmentModel establishmentRes = restTemplate.getForObject("https://api.ratings.food.gov.uk/Establishments/909658", EstablishmentModel.class);			
		
		
		//https://attacomsian.com/blog/spring-boot-resttemplate-get-request-parameters-headers
		
		// make an HTTP GET request with headers
		ResponseEntity<String> response = restTemplate.exchange(
		        url,
		        HttpMethod.GET,
		        request,
		        String.class,
		        1
		);
		
		System.out.println("Business name: " + response.getBody());

	}

	

}
