package com.gwn.xcbl.data.shared;

public class RecaptchaVerifyResultDTO {

	private boolean safe;
	
	private String message;

	public boolean isSafe() {
		return safe;
	}

	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
