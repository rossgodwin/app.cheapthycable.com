package com.gwn.xcbl.data.shared.bill.report;

// TODO rename BillExplorerCritrDTO
public class BillReportCritrDTO {

	private double latitude;
	
	private double longitude;
	
	private double radius;
	
	private double distanceUnit;
	
	private Boolean internetService;
	
	private Boolean cableService;
	
	private Boolean phoneService;

	public BillReportCritrDTO() {
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

	public Boolean getInternetService() {
		return internetService;
	}

	public void setInternetService(Boolean internetService) {
		this.internetService = internetService;
	}

	public Boolean getCableService() {
		return cableService;
	}

	public void setCableService(Boolean cableService) {
		this.cableService = cableService;
	}

	public Boolean getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(Boolean phoneService) {
		this.phoneService = phoneService;
	}
}
