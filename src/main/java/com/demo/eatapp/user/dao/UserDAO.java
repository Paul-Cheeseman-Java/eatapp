package com.demo.eatapp.user.dao;

import java.util.List;

import com.demo.eatapp.user.model.User;

public interface UserDAO {

	public List<User> getUsers();

	public User getUser(String username);
	
	
	public void insertUser(User user);
	
	//public void removeFromList(int fhrsID, String username);
	
	
}
