package com.gwn.xcbl.data.shared.geo;

import java.io.Serializable;

import com.gwn.xcbl.data.shared.ILongId;

public class GeoZipCodeDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8810945592124264273L;

	private Long id = -1L;
	
	private String zipCode;
	
	private String city;
	
	private String stateCode;
	
	public GeoZipCodeDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
}
