package com.gwn.xcbl.data.shared.bill.report;

import java.io.Serializable;

public class BillExplorerStatsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3060874283978194352L;

	private int countOfBills;
	
	private int countOfZipCodes;
	
	private String lowestTotalAmount;
	
	private String averageTotalAmount;
	
	private String highestTotalAmount;

	public BillExplorerStatsDTO() {
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

	public String getLowestTotalAmount() {
		return lowestTotalAmount;
	}

	public void setLowestTotalAmount(String lowestTotalAmount) {
		this.lowestTotalAmount = lowestTotalAmount;
	}

	public String getAverageTotalAmount() {
		return averageTotalAmount;
	}

	public void setAverageTotalAmount(String averageTotalAmount) {
		this.averageTotalAmount = averageTotalAmount;
	}

	public String getHighestTotalAmount() {
		return highestTotalAmount;
	}

	public void setHighestTotalAmount(String highestTotalAmount) {
		this.highestTotalAmount = highestTotalAmount;
	}
}
