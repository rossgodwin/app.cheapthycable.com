package com.gwn.xcbl.data.shared;

import java.io.Serializable;

public class UserDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -35690678997794447L;

	private String latestZipCode;
	
	public UserDataDTO() {
	}

	public String getLatestZipCode() {
		return latestZipCode;
	}

	public void setLatestZipCode(String latestZipCode) {
		this.latestZipCode = latestZipCode;
	}
}
