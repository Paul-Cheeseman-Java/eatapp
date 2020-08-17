package com.demo.eatapp.establishments.model;

public class Pagination {

	private int currentPageNumber;
	private int amountOfPages;

	public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber = currentPageNumber;
	}
	
	public int getAmountOfPages() {
		return amountOfPages;
	}
	
	public void setAmountOfPages(int amountOfPages) {
		this.amountOfPages = amountOfPages;
	}
	
}
