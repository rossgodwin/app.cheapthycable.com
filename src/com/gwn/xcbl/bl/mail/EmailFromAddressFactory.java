package com.gwn.xcbl.bl.mail;

import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.model.AppData;

public class EmailFromAddressFactory {
	
	public static EmailRecipient getNoReplyAddress() {
		return getAddress(AppConstants.APP_NAME + " No-Reply", EmailConstants.PROPERTY_ADDRESS_NO_REPLY);
	}
	
	public static EmailRecipient getHelpAddress() {
		return getAddress(AppConstants.APP_NAME + " Help", EmailConstants.PROPERTY_ADDRESS_HELP);
	}
	
	public static EmailRecipient getAddress(String name, String address) {
		if (AppData.getInstance().isEnvProd()) {
			return new EmailRecipient(name, address);
		} else {
			return new EmailRecipient(null, EmailConstants.PROPERTY_ADDRESS_TEST);
		}
	}
}
