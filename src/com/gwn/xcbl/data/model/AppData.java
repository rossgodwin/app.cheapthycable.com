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

	public void load(Environment environment) {
		setEnvironmentType(environment.getType());
		setDomainUrl(environment.getDomainUrl());
		setDisqusShortname(environment.getDisqusShortname());
		setDisqusApiUrl(environment.getDisqusApiUrl());
		setDisqusApiKey(environment.getDisqusApiKey());
		setDisqusApiSecret(environment.getDisqusApiSecret());
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
}
