package com.gwn.xcbl.data.hibernate.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwn.xcbl.data.entity.GeoZipCodeTableMetadata;

@Entity
@Table(name = GeoZipCodeTableMetadata.TABLE_NAME)
public class GeoZipCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = GeoZipCodeTableMetadata.COL_ID, nullable = false)
	private Long id = -1L;
	
	@Column(name = GeoZipCodeTableMetadata.COL_ZIP_CODE, nullable = false)
	private String zipCode;
	
	@Column(name = "country_code", nullable = false)
	private String countryCode;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "state_code", nullable = false)
	private String stateCode;
	
	@Column(name = "county", nullable = false)
	private String county;
	
	@Column(name = GeoZipCodeTableMetadata.COL_LATITUDE, nullable = false)
	private BigDecimal latitude;
	
	@Column(name = GeoZipCodeTableMetadata.COL_LONGITUDE, nullable = false)
	private BigDecimal longitude;
	
	public GeoZipCode() {
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

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
}
