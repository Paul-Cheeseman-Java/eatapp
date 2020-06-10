package com.demo.eatapp.user.APIController;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.eatapp.establishment.dao.EstablishmentDAO;
import com.demo.eatapp.user.dao.UserDAO;
import com.demo.eatapp.user.model.User;

@RestController
public class UserAPIController {

	@Autowired
	private UserDAO userDAO;
	
	//Matey's
	
	@GetMapping("/user")
	//public void getUsers(@PathVariable String username){
	public ResponseEntity<Object> getUser(){
		User user = userDAO.getUser("test");
		System.out.println("--- User API ---");
		System.out.println("Username: " +user.getUsername());
		System.out.println("Phone: " +user.getPhone());
		System.out.println("Postcode: " +user.getPostcode());
		
		return new ResponseEntity<Object>(user, HttpStatus.OK);
		
	}
	
	@GetMapping("/users")
	//public void getUsers(@PathVariable String username){
	public ResponseEntity<List<User>> getUsers(){
		System.out.println("--- User API ---");
	    List<User> allUsers = userDAO.getUsers();
	    for (User user : allUsers) {
			System.out.println("Username: " +user.getUsername());
	    }
	  
	    return new ResponseEntity<List<User>>(allUsers, HttpStatus.OK);
	}
	
	
}
