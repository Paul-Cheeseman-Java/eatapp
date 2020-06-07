package com.demo.eatapp.establishment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstablishmentModel {



	@JsonProperty("FHRSID")
	private int id;
	@JsonProperty("BusinessName")
	private String businessName;
	@JsonProperty("BusinessType")
    private String businessType;
	@JsonProperty("BusinessTypeID")
    private int businessTypeID;
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
    private int ratingValue;

    public EstablishmentModel() {
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public int getBusinessTypeID() {
		return businessTypeID;
	}

	public void setBusinessTypeID(int businessTypeID) {
		this.businessTypeID = businessTypeID;
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

	public int getRatingValue() {
		return ratingValue;
	}

	public void setRatingValue(int ratingValue) {
		this.ratingValue = ratingValue;
	}
    

    
    
}
