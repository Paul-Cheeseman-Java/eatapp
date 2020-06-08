package com.demo.eatapp.user;

public class User {

	private String username;
	private String password;

	
	public User() {
	}

	public User(String username, String password) {
		//super();
		this.username = username;
		this.password = password;
		//Role is always user
		//id is not required as name is unique
	}



}
