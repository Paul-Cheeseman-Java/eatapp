package com.demo.eatapp.establishment.controller;

import java.util.ArrayList;
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
		   establishmentDAO.removeFromList(recievedFhrsID, "test");
	   }

	
	   @GetMapping(value = "/list")
	   public String getList(Model model) {
		   System.out.println("In Get");
		   Establishments establishments = new Establishments(new ArrayList<Establishment>(establishmentDAO.getList("test")));
		   for (Establishment est : establishments.getEstablishmentList()) {
			   System.out.println("Controller Debug: " + est.getName());
		   }
		   //model.addAttribute("establishments", establishments.getEstablishments());
		   model.addAttribute("establishments", establishments);
		   return "list";
	   }
	   
	   
	   
	   
	   
	@PostMapping(value = "/list")
	   public String postList(@ModelAttribute("establishments") Establishments establishments, Model model) {
		   System.out.println("Post /list hit");
		   System.out.println("Returned list size: " +establishments.getEstablishmentList().size());
		   
		   for (Establishment est : establishments.getEstablishmentList()) {
			   System.out.println("Test Name: " +est.getName());
			   System.out.println("Test Selected?: " +est.isSelected());
			   System.out.println("Test Name: " +est.getPostcode());
			   System.out.println("--------------------------------");
		   }
		   //System.out.println("Whats this? " +establishment.getName());
		   /*
		   @ModelAttribute Establishments establishments
		   System.out.println("List size: " +establishments.getEstablishments().size());
		   for(Establishment establishment : establishments.getEstablishments()) {
			   System.out.println("Looping through: " +establishment.getName());
			   if (establishment.isSelected()) {
				   System.out.println("Selected: " +establishment.getName());
				   establishments.getEstablishments().remove(establishment);
				   System.out.println("After removal of: " +establishment.getName());
			   }
			   System.out.println("Outside IF for: " +establishment.getName());
		   }
		   */
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

			Set<String> idCheck = new HashSet<String>();
			for (Establishment est : establishmentDAO.getList("test")) {
				idCheck.add(est.getFhrsID());
			}
			
			//Establishment apiEstList[] = new Establishment[response.getBody().getEstablishments().length]; 
			List<Establishment> apiEstList = response.getBody().getEstablishmentList();
			
			//Maybe filter out anything but cafes, bars, restaurants - ie places you can sit down to eat/drink
			for (Establishment est : apiEstList) {
				if(idCheck.contains(est.getFhrsID())) {
					est.setSelected(true);
					System.out.println("Is Selected:" + est.getName());					
				}
			}
		   
			
			model.addAttribute("establishments", apiEstList);
			
			
		   return "list";
	   }
	
}
