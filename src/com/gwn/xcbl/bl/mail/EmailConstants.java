package com.gwn.xcbl.bl.mail;

public interface EmailConstants {

	public static final String PROPERTY_MG_API_BASE_URL = "https://api.mailgun.net/v3";
	public static final String PROPERTY_MG_API_KEY = "key-c78d7f15177d7179354f0bc83b07eac5";
	public static final String PROPERTY_MG_DOMAIN = "cheapthycable.com";
	public static final String PROPERTY_MG_TEST_DOMAIN = "sandbox0c1ac161e7cb476e98093d796cab95d1.mailgun.org";
	
	public static final String PROPERTY_ADDRESS_TEST = "mailgun@" + PROPERTY_MG_TEST_DOMAIN;
	public static final String PROPERTY_ADDRESS_NO_REPLY = "no-reply@" + PROPERTY_MG_DOMAIN;
	public static final String PROPERTY_ADDRESS_HELP = "help@" + PROPERTY_MG_DOMAIN;
	
	public static final String PROPERTY_FROM_ADDRESS = "cheapthycable+dev@gmail.com";
	public static final String PROPERTY_FROM_PWD = "rick33ross";
	
	public static final String VARIABLE_LOGO = "logo";
	public static final String VARIABLE_LOGO_IMG_SRC = "logoImgSrc";
	
	public static final String VARIABLE_PRODUCT_NAME = "productName";
	
	public static final String VARIABLE_PRODUCT_URL = "productUrl";
	
	public static final String VARIABLE_CITY_STATE_CODE_ZIP = "cityStateCodeZip";
	
	public static final String VARIABLE_PWD_RESET_URL = "pwdResetUrl";
	public static final String VARIABLE_SIGNUP_VERIFY_URL = "signupVerifyUrl";
	
	public static final String VARIABLE_TWITTER_HOME_PAGE_URL = "twitterHomePageUrl";
	public static final String VARIABLE_FB_HOME_PAGE_URL = "fbHomePageUrl";
}
