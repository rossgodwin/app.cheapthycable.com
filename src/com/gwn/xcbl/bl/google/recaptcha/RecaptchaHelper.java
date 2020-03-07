package com.gwn.xcbl.bl.google.recaptcha;

import org.apache.commons.lang3.StringUtils;

public class RecaptchaHelper {

	public static boolean isSafe(String action, RecaptchaApiSiteVerify verify) {
		if (verify.getSuccess() != null && verify.getSuccess()) {
			if (StringUtils.isNoneEmpty(verify.getAction())) {
				if (action.equalsIgnoreCase(verify.getAction())) {
					if (verify.getScore() != null) {
						return verify.getScore().doubleValue() > 0.6;
					}
				}
			}
		}
		return false;
	}
}
