package com.demo.eatapp.login;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class IoginController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

	
	   @RequestMapping("/")
	   public String indexRoot() {
	      return "index";
	   }
	   
	   @RequestMapping("/index")
	   public String indexExplicit() {
	      return "index";
	   }
	
	   @GetMapping(value = "/homepage")
	   public String getHomepage(Model model) {
		   System.out.println("In Get");
		   model.addAttribute("establishment", new Establishment());
		   return "homepage";
	   }
	   
	   
	   @PostMapping("/homepage")
	   public String postHomepage(RestTemplate restTemplate, @ModelAttribute Establishment establishment) {
		   System.out.println("Name: " +establishment.getName() + ", postcode: " + establishment.getPostcode());

			HttpHeaders headers = new HttpHeaders();
			// set `Content-Type` and `Accept` headers
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.set("x-api-version", "2");
			// build the request
			HttpEntity request = new HttpEntity(headers);

			String url = "https://api.ratings.food.gov.uk/Establishments?address=gu51 3ps";
			//Build request URL
			if (establishment.getName()!=null && establishment.getPostcode()!=null) {
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName() + "&address="+establishment.getPostcode();
			} else if (establishment.getName()!=null){
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName();				
			} else {
				 url = "https://api.ratings.food.gov.uk/Establishments?address="+establishment.getPostcode();
			}
			
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
				//establishmentDAO.addToList(est, "test");
				System.out.println("-   -   -   -   -   -   -  -");
			}
		   
		   return "homepage";
	   }
	   
	   
}
