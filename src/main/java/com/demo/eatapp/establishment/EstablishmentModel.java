package com.demo.eatapp.establishment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EstablishmentModel {

	private int id;
    private String BusinessName;
    private String BusinessType;
    private int BusinessTypeID;
    private String AddressLine1;
    private String AddressLine2;
    private String AddressLine3;
    private String AddressLine4;
    private String PostCode;
    private String Phone;
    private int RatingValue;

    public EstablishmentModel() {
    	
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBusinessName() {
		return BusinessName;
	}
	public void setBusinessName(String businessName) {
		BusinessName = businessName;
	}
	public String getBusinessType() {
		return BusinessType;
	}
	public void setBusinessType(String businessType) {
		BusinessType = businessType;
	}
	public int getBusinessTypeID() {
		return BusinessTypeID;
	}
	public void setBusinessTypeID(int businessTypeID) {
		BusinessTypeID = businessTypeID;
	}
	public String getAddressLine1() {
		return AddressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		AddressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return AddressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		AddressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return AddressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		AddressLine3 = addressLine3;
	}
	public String getAddressLine4() {
		return AddressLine4;
	}
	public void setAddressLine4(String addressLine4) {
		AddressLine4 = addressLine4;
	}
	public String getPostCode() {
		return PostCode;
	}
	public void setPostCode(String postCode) {
		PostCode = postCode;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public int getRatingValue() {
		return RatingValue;
	}
	public void setRatingValue(int ratingValue) {
		RatingValue = ratingValue;
	}

    
    
}
