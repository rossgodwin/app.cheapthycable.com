package com.gwn.xcbl.bl.google.recaptcha;

import java.io.Serializable;

/**
 * https://developers.google.com/recaptcha/docs/verify#api_response
 */
public class RecaptchaApiSiteVerify implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Boolean success;
	
	private Number score;
	
	private String action;
	
	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Number getScore() {
		return score;
	}

	public void setScore(Number score) {
		this.score = score;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
