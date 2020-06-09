package com.demo.eatapp.establishment.dao;


import java.util.List;
import com.demo.eatapp.establishment.model.Establishment;
import com.demo.eatapp.user.User;


public interface EstablishmentDAO {

	public List<Establishment> getList(String username);

	public void addToList(Establishment est, String username);
	
	public void removeFromList(int fhrsID, String username);
	
}
