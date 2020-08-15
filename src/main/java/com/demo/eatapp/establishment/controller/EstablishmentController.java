package com.demo.eatapp.establishment.controller;

import java.security.Principal;
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
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class EstablishmentController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

	
	   @GetMapping(value = "/myList")
	   public String getList(Model model, Principal principal, HttpServletRequest requestForParams) {
		   System.out.println("In Get");

		   pagesArray.clear();
		   
			int requestedPage;
			if (requestForParams.getParameterMap().containsKey("page")){
				requestedPage = Integer.parseInt(requestForParams.getParameter("page"));
				System.out.println("Page Num: " +requestedPage);
			} else {
				requestedPage = 1;
			}

			Establishments est = new Establishments();
			est.setEstablishments(establishmentDAO.getList(principal.getName()));				
			
			//Set selected ticks for user
			for(Establishment e : est.getEstablishments()) {
				if(establishmentDAO.inUsersList(e, principal.getName())) {
					e.setSelected(true);
				}
			}

			
			//Amount of rows in table per page
			int amntOfRows = 8;
			int amtPagesQuotient = est.getEstablishments().size()/amntOfRows;				 
			int amtPagesRemainder = est.getEstablishments().size()%amntOfRows;
			//Catch 'remainder' pages and also increment for results sets below the amount of rows level
			if (amtPagesRemainder > 0 || (amtPagesQuotient == 0 && amtPagesRemainder > 0)) {
				amtPagesQuotient += 1;
			}
			
			for (int i=0; i < amtPagesQuotient; i++) {
				int pageStart = i * amntOfRows; 
				int pageEnd = (i * amntOfRows) +  (amntOfRows); 
				//To ensure that the last page of establishments is the correct size
				if (pageEnd > est.getEstablishments().size()) {
					pageEnd = est.getEstablishments().size();
				}
				
				//https://stackoverflow.com/questions/16644811/converting-a-sublist-of-an-arraylist-to-an-arraylist
				 List<Establishment> pageListing = new ArrayList<Establishment>(est.getEstablishments().subList(pageStart, pageEnd));
				 Establishments pageEst = new Establishments();
				 pageEst.setEstablishmentList(pageListing);
				 pagesArray.add(pageEst);
			}
			
			model.addAttribute("amountOfPages", pagesArray.size());
			model.addAttribute("establishments", pagesArray.get(0));
			model.addAttribute("currentPageNumber", requestedPage);
			model.addAttribute("pageTitle", "Your List");
		   return "myList";
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   @PostMapping(value = "/myList")
	   public String postList(@ModelAttribute("establishments") Establishments establishments, Model model, Principal principal) {
		   System.out.println("Hit post");
		   

		   for (Establishment est : establishments.getEstablishments()) {
			   
		   //Get current list, if item in list, is it now not selected? If so remove
		   //If item not in list and now selected, put it in
			   //System.out.println("In for loop, establishment: " +est.getName());
			   //System.out.println("In for loop, rating date: " +est.getRatingDate());
			   System.out.println("In for loop, type: " +est.getType() +"*");
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
		
		   return "myList";
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
	   
	   
	   
	   //Bit of a hack as couldn't get this to pass on model
	   List<Establishments> pagesArray = new ArrayList<Establishments>();
	   
	   @PostMapping("/search")
	   public String postHomepage(RestTemplate restTemplate, @ModelAttribute Establishment establishment, Model model, Principal principal) {
		   
		   //clear down array for each new search
		   pagesArray.clear();
		   
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

			
			//Amount of rows in table per page
			int amntOfRows = 8;
			int amtPagesQuotient = est.getEstablishments().size()/amntOfRows;				 
			int amtPagesRemainder = est.getEstablishments().size()%amntOfRows;
			//Catch 'remainder' pages and also increment for results sets below the amount of rows level
			if (amtPagesRemainder > 0 || (amtPagesQuotient == 0 && amtPagesRemainder > 0)) {
				amtPagesQuotient += 1;
			}
			
			for (int i=0; i < amtPagesQuotient; i++) {
				int pageStart = i * amntOfRows; 
				int pageEnd = (i * amntOfRows) +  (amntOfRows); 
				//To ensure that the last page of establishments is the correct size
				if (pageEnd > est.getEstablishments().size()) {
					pageEnd = est.getEstablishments().size();
				}
				
				//https://stackoverflow.com/questions/16644811/converting-a-sublist-of-an-arraylist-to-an-arraylist
				 List<Establishment> pageListing = new ArrayList<Establishment>(est.getEstablishments().subList(pageStart, pageEnd));
				 Establishments pageEst = new Establishments();
				 pageEst.setEstablishmentList(pageListing);
				 pagesArray.add(pageEst);
			}
			
			model.addAttribute("amountOfPages", pagesArray.size());
			model.addAttribute("establishments", pagesArray.get(0));
			model.addAttribute("currentPageNumber", 1);
			model.addAttribute("pageTitle", "Search Results");
			
		   return "searchResults";
	   }

	
	
	   
	   @GetMapping(value = "/searchResults")
	   public String getSearchResults(Model model, Principal principal, HttpServletRequest requestForParams) {
	
		   System.out.println("In SearchResult Get");
		   
		   List<Establishment> pageOfEstablishments = new ArrayList<Establishment>();
		   int pageNum = 1;

			if (requestForParams.getParameterMap().containsKey("page")){
				int requestedPage = Integer.parseInt(requestForParams.getParameter("page"));
				
				if (requestedPage > 0 && (requestedPage <= pagesArray.size())) {
					pageNum = requestedPage;
				}
			}
			model.addAttribute("amountOfPages", pagesArray.size());			
			model.addAttribute("currentPageNumber", pageNum);
			model.addAttribute("establishments", pagesArray.get(pageNum -1));
			model.addAttribute("pageTitle", "Search Results");
			
			return "searchResults";
	   }
	   
	   
	   
	   
	   
	   
}
