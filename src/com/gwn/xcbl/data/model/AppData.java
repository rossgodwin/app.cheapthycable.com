package com.gwn.xcbl.data.model;

import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.hibernate.entity.Environment;

public class AppData {

	private static AppData instance;
	
	public static AppData getInstance() {
		if (instance == null) {
			instance = new AppData();
		}
		return instance;
	}
	
	private EnvironmentType environmentType;
	
	private String domainUrl;
	
	private String disqusShortname;
	private String disqusApiUrl;
	private String disqusApiKey;
	private String disqusApiSecret;
	
	private String emailServiceDomain;
	private String emailServiceApiUrl;
	private String emailServiceApiKey;
	
	private String emailUsernameNoReply;
	
	private String recaptchaSiteKey;
	private String recaptchaSecretKey;

	public void load(Environment environment) {
		setEnvironmentType(environment.getType());
		setDomainUrl(environment.getDomainUrl());
		
		setDisqusShortname(environment.getDisqusShortname());
		setDisqusApiUrl(environment.getDisqusApiUrl());
		setDisqusApiKey(environment.getDisqusApiKey());
		setDisqusApiSecret(environment.getDisqusApiSecret());
		
		setEmailServiceDomain(environment.getEmailServiceDomain());
		setEmailServiceApiUrl(environment.getEmailServiceApiUrl());
		setEmailServiceApiKey(environment.getEmailServiceApiKey());
		
		setEmailUsernameNoReply(environment.getEmailUsernameNoReply());
		
		setRecaptchaSiteKey(environment.getRecaptchaSiteKey());
		setRecaptchaSecretKey(environment.getRecaptchaSecretKey());
	}
	
	public String getAppPublicEp() {
		String rslt;
		if (isEnvProd()) {
			rslt = "/client/dist/index-app-public.jsp";
		} else {
			rslt = "/client/src/index-app-public.jsp";
		}
		return rslt;
	}
	
	public String getAppSecureEp() {
		String rslt;
		if (isEnvProd()) {
			rslt = "/client/dist/index-app-secure.jsp";
		} else {
			rslt = "/client/src/index-app-secure.jsp";
		}
		return rslt;
	}
	
	public String getFbAppId() {
		if (isEnvProd()) {
			return AppConstants.FB_PROD_APP_ID;
		}
		return AppConstants.FB_PROD_TEST_ID;
	}
	
	public boolean isEnvDev() {
		return environmentType.equals(EnvironmentType.DEV);
	}
	
	public boolean isEnvProd() {
		return environmentType.equals(EnvironmentType.PROD);
	}

	public EnvironmentType getEnvironmentType() {
		return environmentType;
	}

	public void setEnvironmentType(EnvironmentType environmentType) {
		this.environmentType = environmentType;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getDisqusShortname() {
		return disqusShortname;
	}

	public void setDisqusShortname(String disqusShortname) {
		this.disqusShortname = disqusShortname;
	}

	public String getDisqusApiUrl() {
		return disqusApiUrl;
	}

	public void setDisqusApiUrl(String disqusApiUrl) {
		this.disqusApiUrl = disqusApiUrl;
	}

	public String getDisqusApiKey() {
		return disqusApiKey;
	}

	public void setDisqusApiKey(String disqusApiKey) {
		this.disqusApiKey = disqusApiKey;
	}

	public String getDisqusApiSecret() {
		return disqusApiSecret;
	}

	public void setDisqusApiSecret(String disqusApiSecret) {
		this.disqusApiSecret = disqusApiSecret;
	}

	public String getEmailServiceDomain() {
		return emailServiceDomain;
	}

	public void setEmailServiceDomain(String emailServiceDomain) {
		this.emailServiceDomain = emailServiceDomain;
	}

	public String getEmailServiceApiUrl() {
		return emailServiceApiUrl;
	}

	public void setEmailServiceApiUrl(String emailServiceApiUrl) {
		this.emailServiceApiUrl = emailServiceApiUrl;
	}

	public String getEmailServiceApiKey() {
		return emailServiceApiKey;
	}

	public void setEmailServiceApiKey(String emailServiceApiKey) {
		this.emailServiceApiKey = emailServiceApiKey;
	}

	public String getEmailUsernameNoReply() {
		return emailUsernameNoReply;
	}

	public void setEmailUsernameNoReply(String emailUsernameNoReply) {
		this.emailUsernameNoReply = emailUsernameNoReply;
	}

	public String getRecaptchaSiteKey() {
		return recaptchaSiteKey;
	}

	public void setRecaptchaSiteKey(String recaptchaSiteKey) {
		this.recaptchaSiteKey = recaptchaSiteKey;
	}

	public String getRecaptchaSecretKey() {
		return recaptchaSecretKey;
	}

	public void setRecaptchaSecretKey(String recaptchaSecretKey) {
		this.recaptchaSecretKey = recaptchaSecretKey;
	}
}
