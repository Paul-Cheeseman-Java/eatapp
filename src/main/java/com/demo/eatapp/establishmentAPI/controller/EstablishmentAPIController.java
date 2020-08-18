package com.demo.eatapp.establishmentAPI.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.user.APIController.UserAPIController;
import com.demo.eatapp.user.dao.UserDAO;
import com.demo.eatapp.user.exceptions.UserNotFoundException;
import com.demo.eatapp.user.model.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EstablishmentAPIController {

	
	/* Establishment not found exception required */

	@Autowired
	private EstablishmentDAO establishmentDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping("/establishments/{username}")
	public CollectionModel<Establishment> getEstablishments(@PathVariable String username) {
		System.out.println("--- Establishment API user name: " + username);
		
		User user = userDAO.getUser(username);
		
		if(user==null)
	
			throw new UserNotFoundException("username not found"+ username);
		else {
		
		
				List<Establishment> estList = establishmentDAO.getList(username);

		
				Link link1 = linkTo(UserAPIController.class).slash("establishments").slash(username).withSelfRel();
				Link link2 = linkTo(UserAPIController.class).slash("user").slash(username).withRel("User Details");
				List<Link> linkList = new ArrayList<>();
				linkList.add(link1);
				linkList.add(link2);

				CollectionModel<Establishment> result = CollectionModel.of(estList, linkList);
				return result;
		}
	}
}
