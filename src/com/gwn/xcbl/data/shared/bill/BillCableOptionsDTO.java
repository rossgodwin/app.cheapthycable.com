package com.gwn.xcbl.data.shared.bill;

import java.io.Serializable;

import com.gwn.xcbl.data.shared.ILongId;

public class BillCableOptionsDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4190002088827800327L;

	private Long id = -1L;
	
	private BillDTO bill;
	
	private Integer dvrCount;
	
	private Integer boxCount;
	
	private boolean specialChannels;

	public BillCableOptionsDTO() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BillDTO getBill() {
		return bill;
	}

	public void setBill(BillDTO bill) {
		this.bill = bill;
	}

	public Integer getDvrCount() {
		return dvrCount;
	}

	public void setDvrCount(Integer dvrCount) {
		this.dvrCount = dvrCount;
	}

	public Integer getBoxCount() {
		return boxCount;
	}

	public void setBoxCount(Integer boxCount) {
		this.boxCount = boxCount;
	}

	public boolean isSpecialChannels() {
		return specialChannels;
	}

	public void setSpecialChannels(boolean specialChannels) {
		this.specialChannels = specialChannels;
	}
}
