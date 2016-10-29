package com.gwn.xcbl.bl.mail.jrsy;

import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.data.model.AppData;

public class JrsyEmailConfigurationFactory {

	public static JrsyEmailConfiguration getConfig() {
		JrsyEmailConfiguration config = new JrsyEmailConfiguration();
		config.setApiBaseUrl(EmailConstants.PROPERTY_MG_API_BASE_URL);
		config.setApiKey(EmailConstants.PROPERTY_MG_API_KEY);
		if (AppData.getInstance().isEnvProd()) {
			config.setDomain(EmailConstants.PROPERTY_MG_DOMAIN);
		} else {
			config.setDomain(EmailConstants.PROPERTY_MG_TEST_DOMAIN);
		}
		return config;
	}
}
