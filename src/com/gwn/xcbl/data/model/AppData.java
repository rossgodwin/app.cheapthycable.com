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

	public void load(Environment environment) {
		setEnvironmentType(environment.getType());
		setDomainUrl(environment.getDomainUrl());
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
}
