package com.gwn.xcbl.bl.mail;

/**
 * Careful modifying variables prefixed with VARIABLE_ as these are used in email templates (WebContent\WEB-INF\templates\emails)
 */
public interface EmailConstants {
	
	public static final String VARIABLE_LOGO = "logo";
	public static final String VARIABLE_LOGO_IMG_SRC = "logoImgSrc";
	
	public static final String VARIABLE_PRODUCT_NAME = "productName";
	
	public static final String VARIABLE_PRODUCT_URL = "productUrl";
	
	public static final String VARIABLE_CITY_STATE_CODE_ZIP = "cityStateCodeZip";
	
	public static final String VARIABLE_PWD_RESET_URL = "pwdResetUrl";
	public static final String VARIABLE_SIGNUP_VERIFY_URL = "signupVerifyUrl";
	
	public static final String VARIABLE_TWITTER_HOME_PAGE_URL = "twitterHomePageUrl";
	public static final String VARIABLE_FB_HOME_PAGE_URL = "fbHomePageUrl";
	
	public static final String VARIABLE_BILL_COUNT = "billCount";
	public static final String VARIABLE_BILLS = "bills";
	public static final String VARIABLE_BILL_COMMENT_RESPOND_URL = "billCommentRespondUrl";
	
	public static final String VARIABLE_UNSUBSCRIBE_URL = "unsubscribeUrl";
	
	public static final String VARIABLE_DSQ_POST_TEXT = "dsqPostText";
}
