package com.gwn.xcbl.data.model;

// TODO rename HaversineFormulaVars or HaversineFormulaParams ??
public class HaversineFormulaCritr {
	
	private double latitude;
	
	private double longitude;
	
	private double radius;
	
	private double distanceUnit;
	
	public HaversineFormulaCritr() {
	}
	
	public HaversineFormulaCritr(double latitude, double longitude, double radius, double distanceUnit) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
		this.distanceUnit = distanceUnit;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getDistanceUnit() {
		return distanceUnit;
	}

	public void setDistanceUnit(double distanceUnit) {
		this.distanceUnit = distanceUnit;
	}
}
