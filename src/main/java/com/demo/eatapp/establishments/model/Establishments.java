package com.demo.eatapp.establishments.model;

import java.util.List;

import com.demo.eatapp.establishment.model.Establishment;

public class Establishments {

	private List<Establishment> establishmentList; 
	
	public Establishments() {
		
	}

	public List<Establishment> getEstablishmentList() {
		return establishmentList;
	}

	public void setEstablishmentList(List<Establishment> establishmentList) {
		this.establishmentList = establishmentList;
	}

	//Renamed getter for ResponseEntity
	public List<Establishment> getEstablishments() {
		return establishmentList;
	}
	
	//Renamed setter for ResponseEntity	
	public void setEstablishments(List<Establishment> establishmentList) {
		this.establishmentList = establishmentList;
	}
}
