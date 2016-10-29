package com.gwn.xcbl.data.shared.bill.create;

public class BillCreateCableOptionsDTO {

	private int dvrCount;
	
	private int boxCount;
	
	private boolean specialChannels;

	public int getDvrCount() {
		return dvrCount;
	}

	public void setDvrCount(int dvrCount) {
		this.dvrCount = dvrCount;
	}

	public int getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(int boxCount) {
		this.boxCount = boxCount;
	}

	public boolean isSpecialChannels() {
		return specialChannels;
	}

	public void setSpecialChannels(boolean specialChannels) {
		this.specialChannels = specialChannels;
	}
}
