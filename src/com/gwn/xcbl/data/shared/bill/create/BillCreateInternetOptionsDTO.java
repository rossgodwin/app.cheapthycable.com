package com.gwn.xcbl.data.shared.bill.create;

public class BillCreateInternetOptionsDTO {

	private boolean modem;
	
	private Integer downloadSpeedMbps;
	
	private Integer downloadSpeedMbpsRngLower;
	
	private Integer downloadSpeedMbpsRngUpper;

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
}
