package com.demo.eatapp.user.APIController;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.establishmentAPI.controller.EstablishmentAPIController;
import com.demo.eatapp.user.dao.UserDAO;
import com.demo.eatapp.user.exceptions.UserNotFoundException;
import com.demo.eatapp.user.model.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class UserAPIController {

	@Autowired
	private UserDAO userDAO;
	
	//Matey's
	@GetMapping("/user/{username}")
	public EntityModel<User> retrieveUser(@PathVariable String username) {
		System.out.println("--- User API, name: " + username);
		User user = userDAO.getUser(username);
		
		if(user==null)
			throw new UserNotFoundException("username-"+ username);
		else {
	        Link selfLink = linkTo(UserAPIController.class).slash(username).withSelfRel();
	        Link link = linkTo(UserAPIController.class).slash(username).withRel("allEstablishments");
	        user.add(selfLink);
	        user.add(link);
		    EntityModel<User> result = EntityModel.of(user);
	        
			return result;			
		}
	}

	
	@GetMapping("/users")
	//public void getUsers(@PathVariable String username){
	public CollectionModel<User> getUsers(){
		System.out.println("--- User API ---");
	    List<User> allUsers = userDAO.getUsers();
	    
	    for (User user : allUsers) {
	        String username = user.getUsername();
	        Link selfLink = linkTo(UserAPIController.class).slash(username).withSelfRel();
	        
	        user.add(selfLink);
	        /*
	         * THIS IS LINK FOR GETTING ALL ESTABLISHMENTS FOR A SPECIFIC CUSTOMER
	        if (orderService.getAllOrdersForCustomer(customerId).size() > 0) {
	            Link ordersLink = linkTo(methodOn(CustomerController.class)
	              .getOrdersForCustomer(customerId)).withRel("allOrders");
	            customer.add(ordersLink);
	        }
	        */
	    }
	 
	    Link link = linkTo(UserAPIController.class).withSelfRel();
	    CollectionModel<User> result = CollectionModel.of(allUsers, link);
	    return result;
	}
	

	
}



