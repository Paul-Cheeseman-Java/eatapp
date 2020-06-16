package com.demo.eatapp.establishment.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class EstablishmentController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

	   @GetMapping("/delete")
	   public void removeFromList(HttpServletRequest request) {
		   int recievedFhrsID = Integer.parseInt(request.getParameter("id"));
		   System.out.println("----- Deleting: " + recievedFhrsID + " ----------------");
		   establishmentDAO.removeFromList(establishmentDAO.getEstablishment(recievedFhrsID, "test"), "test");
	   }

	
	   @GetMapping(value = "/list")
	   public String getList(Model model) {
		   System.out.println("In Get");
		   //VARIABLE FOR TITLE - "PERSONAL LIST" (search would be "SEARCH RESULTS" (maybe note to say those in your list selected)
		   Establishments establishmentsList = new Establishments();
		   establishmentsList.setEstablishments(establishmentDAO.getList("test"));
		   model.addAttribute("establishments", establishmentsList);
		   return "list";
	   }
	   
	   
	   
	   @PostMapping(value = "/list")
	   public String postList(@ModelAttribute("establishments") Establishments establishments, Model model) {
		   System.out.println("Hit post");
		   for (Establishment est : establishments.getEstablishments()) {
		   //Get current list, if item in list, is it now not selected? If so remove
		   //If item not in list and now selected, put it in
			   if(establishmentDAO.inUsersList(est, "test")) {
				   if(!est.isSelected()) {
					   establishmentDAO.removeFromList(est, "test");
				   }
			   } else if (est.isSelected()) {
				establishmentDAO.addToList(est, "test");
			   }
		   }
		   Establishments establishmentsList = new Establishments();
		   establishmentsList.setEstablishments(establishmentDAO.getList("test"));
		   model.addAttribute("establishments", establishmentsList);
		   return "list";
	   }
	

	   @GetMapping(value = "/search")
	   public String getHomepage(Model model) {
		   System.out.println("In Get");
		   model.addAttribute("establishment", new Establishment());
		   return "search";
	   }

	   //Utility method to validate user input
	   public static boolean isNotNullNotEmptyNotOnlyWhiteSpace(final String string)  
	   {  
		   return string != null && !string.isEmpty() && !string.trim().isEmpty();  
	   } 
	   
	   @PostMapping("/search")
	   public String postHomepage(RestTemplate restTemplate, @ModelAttribute Establishment establishment, Model model) {
		   System.out.println("Name: " +establishment.getName() + ", postcode: " + establishment.getPostcode());

			HttpHeaders headers = new HttpHeaders();
			// set `Content-Type` and `Accept` headers
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("x-api-version", "2");
			// build the request\
			HttpEntity request = new HttpEntity(headers);
			String url = "";
	System.out.println("Debug #1");
			if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getName()) && isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getPostcode())) {
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName() + "&address="+establishment.getPostcode();
	System.out.println("Debug url #1: " +url);				 
			} else if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getName())){
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName();
	System.out.println("Debug url #2: " +url);				 				 
			} else if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getPostcode())){
				 url = "https://api.ratings.food.gov.uk/Establishments?address="+establishment.getPostcode();
	System.out.println("Debug url #3: " +url);				 				 
			} else {
				//send error msg
	System.out.println("Debug url #4: " +url);				 				
			}

			// make an HTTP GET request with headers
			ResponseEntity<Establishments> response = restTemplate.exchange(
			        url,
			        HttpMethod.GET,
			        request,
			        Establishments.class
			);

			List<Establishment> testList = response.getBody().getEstablishments();

			Establishments test = new Establishments();
			test.setEstablishments(testList);
			//System.out.println("Debug response has body: " + response.hasBody());
			//System.out.println("Debug response get body: " + response.getBody());
			//System.out.println("Debug response toString: " + response.toString());
			System.out.println("Anything after #1");
			
			for(Establishment e : test.getEstablishments()) {
				System.out.println("Name: " +e.getName());
			}
			
			
			
			model.addAttribute("establishments", test);
		   return "list";
	   }
	
}
