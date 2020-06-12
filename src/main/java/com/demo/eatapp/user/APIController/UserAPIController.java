package com.demo.eatapp.user.APIController;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
			/* Improve message in the bespoke user exception! */
			throw new UserNotFoundException("username-"+ username);
		else {
	        Link selfLink = linkTo(UserAPIController.class).slash("user").slash(username).withSelfRel();
	        Link link = linkTo(UserAPIController.class).slash("establishments").slash(username).withRel("User Establishments");
	        Link test = linkTo(UserAPIController.class).slash("users").withRel("All Users");
	        user.add(selfLink);
	        user.add(link);
	        user.add(test);
	        
	        EntityModel<User> result = EntityModel.of(user);
	        
			return result;			
		}
	}

	
	@GetMapping("/users")
	public CollectionModel<User> getUsers(){
	    List<User> allUsers = userDAO.getUsers();
	    
	    for (User user : allUsers) {
	        String username = user.getUsername();
	        Link link1 = linkTo(UserAPIController.class).slash("users").slash(username).withSelfRel();
	        Link link2 = linkTo(UserAPIController.class).slash("establishments").slash(username).withRel("User Establishments");
	        user.add(link1);
	        user.add(link2);
	    }
	    CollectionModel<User> result = CollectionModel.of(allUsers);
	    return result;
	}
	

	
}



