package com.gwn.xcbl.data.shared.bill;

import java.io.Serializable;

import com.gwn.xcbl.data.shared.ILongId;

public class BillInternetOptionsDTO implements Serializable, ILongId {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7943710941487667624L;

	private Long id = -1L;
	
	private BillDTO bill;
	
	private boolean modem;
	
	private Integer downloadSpeedMbps;
	
	private Integer downloadSpeedMbpsRngLower;
	
	private Integer downloadSpeedMbpsRngUpper;
	
	private Integer uploadSpeedMbps;
	
	public BillInternetOptionsDTO() {}

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

	public boolean isModem() {
		return modem;
	}

	public void setModem(boolean modem) {
		this.modem = modem;
	}

	public Integer getDownloadSpeedMbps() {
		return downloadSpeedMbps;
	}

	public void setDownloadSpeedMbps(Integer downloadSpeedMbps) {
		this.downloadSpeedMbps = downloadSpeedMbps;
	}

	public Integer getDownloadSpeedMbpsRngLower() {
		return downloadSpeedMbpsRngLower;
	}

	public void setDownloadSpeedMbpsRngLower(Integer downloadSpeedMbpsRngLower) {
		this.downloadSpeedMbpsRngLower = downloadSpeedMbpsRngLower;
	}

	public Integer getDownloadSpeedMbpsRngUpper() {
		return downloadSpeedMbpsRngUpper;
	}

	public void setDownloadSpeedMbpsRngUpper(Integer downloadSpeedMbpsRngUpper) {
		this.downloadSpeedMbpsRngUpper = downloadSpeedMbpsRngUpper;
	}

	public Integer getUploadSpeedMbps() {
		return uploadSpeedMbps;
	}

	public void setUploadSpeedMbps(Integer uploadSpeedMbps) {
		this.uploadSpeedMbps = uploadSpeedMbps;
	}
}
