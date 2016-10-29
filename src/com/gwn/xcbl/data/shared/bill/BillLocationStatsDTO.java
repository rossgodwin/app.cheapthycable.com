package com.gwn.xcbl.data.shared.bill;

import java.io.Serializable;
import java.math.BigDecimal;

public class BillLocationStatsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 58812945440350646L;

	private double mileRadius;
	
	private int countOfBills;
	
	private int countOfZipCodes;
	
	private BigDecimal lowestTotalAmount;
	
	private BigDecimal averageTotalAmount;
	
	private BigDecimal highestTotalAmount;

	public BillLocationStatsDTO() {
	}
	
	public double getMileRadius() {
		return mileRadius;
	}

	public void setMileRadius(double mileRadius) {
		this.mileRadius = mileRadius;
	}
	
	public int getCountOfBills() {
		return countOfBills;
	}

	public void setCountOfBills(int countOfBills) {
		this.countOfBills = countOfBills;
	}

	public int getCountOfZipCodes() {
		return countOfZipCodes;
	}

	public void setCountOfZipCodes(int countOfZipCodes) {
		this.countOfZipCodes = countOfZipCodes;
	}

	public BigDecimal getLowestTotalAmount() {
		return lowestTotalAmount;
	}

	public void setLowestTotalAmount(BigDecimal lowestTotalAmount) {
		this.lowestTotalAmount = lowestTotalAmount;
	}

	public BigDecimal getAverageTotalAmount() {
		return averageTotalAmount;
	}

	public void setAverageTotalAmount(BigDecimal averageTotalAmount) {
		this.averageTotalAmount = averageTotalAmount;
	}

	public BigDecimal getHighestTotalAmount() {
		return highestTotalAmount;
	}

	public void setHighestTotalAmount(BigDecimal highestTotalAmount) {
		this.highestTotalAmount = highestTotalAmount;
	}
}
