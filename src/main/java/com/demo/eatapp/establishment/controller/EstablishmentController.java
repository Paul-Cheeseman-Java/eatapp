package com.demo.eatapp.establishment.controller;

import java.security.Principal;
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
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class EstablishmentController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

		/*
	   @GetMapping("/delete")
	   public void removeFromList(HttpServletRequest request, Principal principal) {
		   int recievedFhrsID = Integer.parseInt(request.getParameter("id"));
		   System.out.println("----- Deleting: " + recievedFhrsID + " ----------------");
		   establishmentDAO.removeFromList(establishmentDAO.getEstablishment(recievedFhrsID, principal.getName()), principal.getName());
	   }
		*/
	
	   @GetMapping(value = "/list")
	   public String getList(Model model, Principal principal) {
		   System.out.println("In Get");
		   //VARIABLE FOR TITLE - "PERSONAL LIST" (search would be "SEARCH RESULTS" (maybe note to say those in your list selected)

		   //When list is empty, need msg
			Establishments est = new Establishments();
			est.setEstablishments(establishmentDAO.getList(principal.getName()));
			model.addAttribute("establishments", est);
			model.addAttribute("pageTitle", "Your List");

		   return "list";
	   }
	   
	   
	   
	   @PostMapping(value = "/list")
	   public String postList(@ModelAttribute("establishments") Establishments establishments, Model model, Principal principal) {
		   System.out.println("Hit post");
		   
		   
		   for (Establishment est : establishments.getEstablishments()) {
			   
		   //Get current list, if item in list, is it now not selected? If so remove
		   //If item not in list and now selected, put it in
			   System.out.println("In for loop, establishment: " +est.getName());
			   System.out.println("In for loop, rating date: " +est.getRatingDate());
			   if(establishmentDAO.inUsersList(est, principal.getName())) {
				   System.out.println("In list");
				   if(!est.isSelected()) {
					   System.out.println("In list but not selected");
					   establishmentDAO.removeFromList(est, principal.getName());
				   }
			   } else if (est.isSelected()) {
				System.out.println("Not in list, new addition");
				establishmentDAO.addToList(est, principal.getName());
			   }
		   }
		   
			Establishments est = new Establishments();
			est.setEstablishments(establishmentDAO.getList(principal.getName()));
			model.addAttribute("establishments", est);
			model.addAttribute("pageTitle", "Your List");

			
			
			for (Establishment e : est.getEstablishmentList()) {
				   System.out.println("List POST: " +e.getName());
			}
			
		   return "list";
	   }
	

	   @GetMapping(value = "/search")
	   public String getHomepage(Model model) {
		   model.addAttribute("establishment", new Establishment());
		   return "search";
	   }

	   //Utility method to validate user input
	   public static boolean isNotNullNotEmptyNotOnlyWhiteSpace(final String string)  
	   {  
		   return string != null && !string.isEmpty() && !string.trim().isEmpty();  
	   } 
	   
	   @PostMapping("/search")
	   public String postHomepage(RestTemplate restTemplate, @ModelAttribute Establishment establishment, Model model, Principal principal) {

		   HttpHeaders headers = new HttpHeaders();
			// set `Content-Type` and `Accept` headers
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("x-api-version", "2");
			// build the request\
			HttpEntity request = new HttpEntity(headers);
			String url = "";
			if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getName()) && isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getPostcode())) {
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName() + "&address="+establishment.getPostcode();
			} else if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getName())){
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName();
			} else if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getPostcode())){
				 url = "https://api.ratings.food.gov.uk/Establishments?address="+establishment.getPostcode();
			} else {
				//send error msg
			}

			// make an HTTP GET request with headers
			ResponseEntity<Establishments> response = restTemplate.exchange(
			        url,
			        HttpMethod.GET,
			        request,
			        Establishments.class
			);

			List<Establishment> estList = response.getBody().getEstablishments();

			Establishments est = new Establishments();
			est.setEstablishments(estList);

			//Set selected ticks for user
			for(Establishment e : est.getEstablishments()) {
				if(establishmentDAO.inUsersList(e, principal.getName())) {
					e.setSelected(true);
				}
				
			}
			
		   //When list is empty, need msg
			
			for (Establishment e : est.getEstablishmentList()) {
				   System.out.println("Search POST: " +e.getName());
			}
			
			
			model.addAttribute("establishments", est);
			model.addAttribute("pageTitle", "Search Results");
			
		   return "list";
	   }
	
}
