package com.demo.eatapp.establishments.model;

import java.util.ArrayList;
import java.util.List;

import com.demo.eatapp.establishment.model.Establishment;

public class Establishments {

	private ArrayList<Establishment> establishmentList; 
	
	public Establishments() {
		
	}

	
	public Establishments(ArrayList<Establishment> establishments) {
		this.establishmentList = establishments;
	}
	
	public ArrayList<Establishment> getEstablishmentList() {
		return establishmentList;
	}

	public void setEstablishmentList(ArrayList<Establishment> establishments) {
		this.establishmentList = establishments;
	}

	
}
