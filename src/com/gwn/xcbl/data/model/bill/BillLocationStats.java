package com.gwn.xcbl.data.model.bill;

import java.math.BigDecimal;

public class BillLocationStats {

	private int countOfBills;
	
	private int countOfZipCodes;
	
	private BigDecimal lowestTotalAmount;
	
	private BigDecimal averageTotalAmount;
	
	private BigDecimal highestTotalAmount;

	public BillLocationStats() {
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
