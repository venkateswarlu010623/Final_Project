package com.Employee.modal;


public class Location {

	int locationId;
	String locationName;
	String locationCountry;
	

	public Location() {
		super();
	}
	
	public Location(int locationId, String locationName, String locationCountry) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
		this.locationCountry = locationCountry;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getLocationCountry() {
		return locationCountry;
	}

	public void setLocationCountry(String locationCountry) {
		this.locationCountry = locationCountry;
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", locationName=" + locationName + ", locationCountry="
				+ locationCountry + "]";
	}
	
}

//Location
//--------
//locationId
//locationName (Hyderabad, chennai, London)
//locationCountry (India, United Kingdom)