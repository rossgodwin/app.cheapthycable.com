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
	
	private Environment environment;

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public String getFbAppId() {
		if (isEnvProd()) {
			return AppConstants.FB_PROD_APP_ID;
		}
		return AppConstants.FB_PROD_TEST_ID;
	}
	
	public boolean isEnvDev() {
		return environment.getType().equals(EnvironmentType.DEV);
	}
	
	public boolean isEnvProd() {
		return environment.getType().equals(EnvironmentType.PROD);
	}
}
