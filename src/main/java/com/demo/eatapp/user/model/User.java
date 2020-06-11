package com.demo.eatapp.user.model;

import org.springframework.hateoas.RepresentationModel;

public class User extends RepresentationModel<User>{

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int age;
	private String postcode;
	private String phone;
	
	public User() {
	}

	public User(String username, String password, String firstName, String lastName, int age, String postcode, String phone) {
		//Role is always user
		//id is not required as name is unique
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.postcode = postcode;
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
