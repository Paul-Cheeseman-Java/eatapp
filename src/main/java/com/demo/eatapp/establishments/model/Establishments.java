package com.demo.eatapp.establishments.model;

import java.util.List;

import com.demo.eatapp.establishment.model.Establishment;

public class Establishments {

	private List<Establishment> establishmentList; 

	private static Pagination pagination = new Pagination();
	
	public Establishments() {

	}
	
	
	public Establishments(List<Establishment> establishmentList) {
		this.establishmentList = establishmentList;
	}

	
	public static Pagination getPagination() {
		return pagination;
	}


	public int getSize() {
		return establishmentList.size();
	}

	public List<Establishment> getEstablishmentList() {
		return establishmentList;
	}

	public void setEstablishmentList(List<Establishment> establishmentList) {
		this.establishmentList = establishmentList;
	}
	
	public void removeFromList(Establishment establishment){
		this.establishmentList.remove(establishment);
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
