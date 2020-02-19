package com.gwn.xcbl.bl.mail.jrsy;

import com.gwn.xcbl.data.model.AppData;

public class JrsyEmailConfigurationFactory {

	public static JrsyEmailConfiguration getConfig() {
		JrsyEmailConfiguration config = new JrsyEmailConfiguration();
		config.setApiBaseUrl(AppData.getInstance().getEmailServiceApiUrl());
		config.setApiKey(AppData.getInstance().getEmailServiceApiKey());
		config.setDomain(AppData.getInstance().getEmailServiceDomain());
		return config;
	}
}
