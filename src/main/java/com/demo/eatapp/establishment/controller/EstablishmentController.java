package com.demo.eatapp.establishment.controller;

import java.util.Collections;
import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.establishments.model.Establishments;

@Controller
public class EstablishmentController {

	@Autowired
	private EstablishmentDAO establishmentDAO;

	
	@GetMapping("/list")
	public void getList() {
		List<Establishment> listOfEstablishment = establishmentDAO.getList("test");
		//Maybe filter out anything but cafes, bars, restaurants - ie places you can sit down to eat/drink
		int val = 0;
		for (Establishment est : listOfEstablishment) {
			val++;
			System.out.println("----- List Ouput #" + val + " ----------------");
			System.out.println("Establishment id: " + est.getFhrsID());
			System.out.println("Establishment name: " + est.getName());
			establishmentDAO.addToList(est, "test");
			System.out.println("-   -   -   -   -   -   -  -");
		}
		
	}
	

	@GetMapping("/delete")
	public void removeFromList(HttpServletRequest request) {
		int recievedFhrsID = Integer.parseInt(request.getParameter("id"));
		System.out.println("----- Deleting: " + recievedFhrsID + " ----------------");
		establishmentDAO.removeFromList(recievedFhrsID, "test");
	}


}
