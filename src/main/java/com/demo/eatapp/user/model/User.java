package com.demo.eatapp.user.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

public class User extends RepresentationModel<User>{

	@NotNull
	@Size(min=2, max=20, message = "Username must be between 2 and 20 characters")
	private String username;
	
	@NotNull
	@Size(min=8, max=30, message = "Password must be between 8 and 30 characters")
	private String password;
	
	@NotNull
	@Size(min=2, max=20, message = "First name must be between 2 and 20 characters")
	private String firstName;

	@NotNull
	@Size(min=2, max=20, message = "Last name must be between 2 and 20 characters")
	private String lastName;

	@NotNull
	@Min(value=16, message = "You need to be at least 16 to register")
	@Max(value=100, message = "Users over 100 need to be verified")
	private int age;
	
	@NotNull
	@Size(min=11, max=11, message = "Phone number must be 11 digits")
	private String phone;
	
	@NotNull
	@Size(min=7, max=8, message = "Postcode must be between 7-8 characters")
	private String postcode;
	
	public User() {
	}

	public User(String username, String password, String firstName, String lastName, int age, String postcode) {
		//Role is always user
		//id is not required as name is unique
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.postcode = postcode;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
