package com.demo.eatapp.establishment.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.demo.eatapp.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Establishment extends RepresentationModel<Establishment>{


	@JsonProperty("FHRSID")
	private String fhrsID;
	@JsonProperty("BusinessName")
	private String name;
	@JsonProperty("BusinessType")
    private String type;
	@JsonProperty("BusinessTypeID")
    private String TypeID;
	@JsonProperty("AddressLine1")
    private String addressLine1;
	@JsonProperty("AddressLine2")
    private String addressLine2;
	@JsonProperty("AddressLine3")
    private String addressLine3;
	@JsonProperty("AddressLine4")
    private String addressLine4;
	@JsonProperty("PostCode")
    private String postcode;
	@JsonProperty("Phone")
    private String phone;
	@JsonProperty("RatingValue")
    private String ratingValue;
	@JsonProperty("RatingDate")
    private String ratingDate;
    private boolean selected = false;

    public Establishment() {
    	
    }



	public String getFhrsID() {
		return fhrsID;
	}



	public void setFhrsID(String fhrsID) {
		this.fhrsID = fhrsID;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getTypeID() {
		return TypeID;
	}



	public void setTypeID(String typeID) {
		TypeID = typeID;
	}



	public String getAddressLine1() {
		return addressLine1;
	}



	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}



	public String getAddressLine2() {
		return addressLine2;
	}



	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}



	public String getAddressLine3() {
		return addressLine3;
	}



	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}



	public String getAddressLine4() {
		return addressLine4;
	}



	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
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



	public String getRatingValue() {
		return ratingValue;
	}



	public void setRatingValue(String ratingValue) {
		this.ratingValue = ratingValue;
	}



	public String getRatingDate() {
		if (ratingDate.contains("T00:00:00")) {
			String[] ratingDateOnly = ratingDate.split("T");
			String dateOnly = ratingDateOnly[0]; // 004
			return dateOnly;			
		}
		return ratingDate;
	}



	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}



	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	
	
}
