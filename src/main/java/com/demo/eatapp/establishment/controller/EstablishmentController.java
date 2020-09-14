package com.demo.eatapp.establishment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class EstablishmentController {

	@Autowired
	private EstablishmentDAO establishmentDAO;



	   @GetMapping(value = "/search")
	   public String getHomepage(Model model) {
		   model.addAttribute("establishment", new Establishment());
		   return "search";
	   }

	
	
	   @GetMapping(value = "/myList")
	   public String getList(Model model, Principal principal, HttpServletRequest requestForParams) {
		   System.out.println("In Get");
		   
		   pagesArray.clear();
		   
			int requestedPage;
			if (requestForParams.getParameterMap().containsKey("page")){
				requestedPage = Integer.parseInt(requestForParams.getParameter("page"));
				//System.out.println("Page Num: " +requestedPage);
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
			
			System.out.println("Debug - pagesArray.size(): " +pagesArray.size());
			
			//If last element in pagesArray is removed (or list looked at before a search is run), 
			//then need to send an empty List<Establishment> otherwise it will fail as nothing there 
			//to reference!
			if (pagesArray.isEmpty()){
				Establishments emptyist = new Establishments(new ArrayList<Establishment>());
				model.addAttribute("establishments", emptyist);
			} else {
				model.addAttribute("establishments", pagesArray.get(requestedPage -1));
			}

			model.addAttribute("amountOfPages", pagesArray.size());
			model.addAttribute("currentPageNumber", requestedPage);
		   return "myList";
	   }
	   

	   @PostMapping(value = "/myList")
	   public ModelAndView postList(@ModelAttribute("establishments") Establishments establishments, Model model, Principal principal, HttpServletRequest requestForParams) {

		   System.out.println("Hit post");
		   
			   System.out.println("Hit post");

			   Iterator<Establishment> estabIterator = establishments.getEstablishmentList().iterator();
			   while (estabIterator.hasNext()) {
				   Establishment iteratorItem = estabIterator.next();
				   
				   if(establishmentDAO.inUsersList(iteratorItem, principal.getName())) {
					   if(!iteratorItem.isSelected()) {
						   System.out.println("In list but not selected - Removing: " +iteratorItem.getName());
						   establishmentDAO.removeFromList(iteratorItem, principal.getName());
						   estabIterator.remove();
					   }
				   }
				}

			ModelAndView modelAndView = new ModelAndView("redirect:/myList");
			return modelAndView;
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
			
			/*
			 * Hacky to use the postcode field it is (for interaction between form and API call), ie for any address entry
			 * Ideally find and implement a cleaner solution.
			 */
			
			
			if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getName()) && isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getPostcode())) {
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName() + "&address="+establishment.getPostcode();
			} else if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getName())){
				 url = "https://api.ratings.food.gov.uk/Establishments?name=" + establishment.getName();
			} else if (isNotNullNotEmptyNotOnlyWhiteSpace(establishment.getPostcode())){
				 url = "https://api.ratings.food.gov.uk/Establishments?address="+establishment.getPostcode();
			} else {
				establishment.setName("");
				establishment.setPostcode("");
				return "search";
			}

			// make an HTTP GET request with headers
			ResponseEntity<Establishments> response = restTemplate.exchange(
			        url,
			        HttpMethod.GET,
			        request,
			        Establishments.class
			);

			
			List<Establishment> estList = response.getBody().getEstablishments();
			
			if (estList.isEmpty()) {
				System.out.println("DEBUG: Empty repsonse detected, should go back to search!");
				model.addAttribute("noResults", true);
				return "search";
			} else if (estList.size() > 40) {
				System.out.println("Too many results, should be flagged");
				model.addAttribute("tooManyResults", true);
				return "search";
			}			
			
			Establishments est = new Establishments(estList);

			for(Establishment e : est.getEstablishments()) {
				//Set selected ticks for user
				if(establishmentDAO.inUsersList(e, principal.getName())) {
					e.setSelected(true);
				}
				//Protecting app against dodgey FSA API value lengths...
				//DB fields already big, but this is a belt and braces
				if (e.getFhrsID().length() > 120) {
					e.setFhrsID(e.getFhrsID().substring(0,119));
				}
				if (e.getName().length() > 120) {
					e.setName(e.getName().substring(0,119));
				}
				if (e.getType().length() > 135) {
					e.setType(e.getType().substring(0,134));
				}
				if (e.getTypeID().length() > 20) {
					e.setTypeID(e.getTypeID().substring(0,19));
				}
				if (e.getAddressLine1().length() > 90) {
					e.setAddressLine1(e.getAddressLine1().substring(0,89));
				}
				if (e.getAddressLine2().length() > 90) {
					e.setAddressLine2(e.getAddressLine2().substring(0,89));
				}
				if (e.getAddressLine3().length() > 90) {
					e.setAddressLine3(e.getAddressLine3().substring(0,89));
				}
				if (e.getAddressLine4().length() > 90) {
					e.setAddressLine4(e.getAddressLine4().substring(0,89));
				}
				if (e.getPostcode().length() > 8) {
					e.setPostcode(e.getPostcode().substring(0,7));
				}
				if (e.getRatingValue().length() > 30) {
					e.setRatingValue(e.getRatingValue().substring(0,29));
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
			
				 Establishments pageEst = new Establishments(pageListing);
				 pagesArray.add(pageEst);
			}


			model.addAttribute("amountOfPages", pagesArray.size());
			model.addAttribute("establishments", pagesArray.get(0));
			model.addAttribute("currentPageNumber", 1);
			
		   return "searchResults";
	   }

	
	
	   
	   @GetMapping(value = "/searchResults")
	   public String getSearchResults(Model model, Principal principal, HttpServletRequest requestForParams) {
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
			return "searchResults";
	   }
	   

	   @PostMapping(value = "/searchResults")
	   public ModelAndView postSearchResults(@ModelAttribute("establishments") Establishments establishments, Model model, Principal principal, HttpServletRequest requestForParams) {
		   Iterator<Establishment> estabIterator = establishments.getEstablishmentList().iterator();
		   while (estabIterator.hasNext()) {
			   Establishment iteratorItem = estabIterator.next();
			   if(iteratorItem.isSelected() && !establishmentDAO.inUsersList(iteratorItem, principal.getName())) {
				   System.out.println("New Entry....: " +iteratorItem.getName());
				   establishmentDAO.addToList(iteratorItem, principal.getName());
			   }
			}
			ModelAndView modelAndView = new ModelAndView("redirect:/myList");
			return modelAndView;
	   }
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
}
