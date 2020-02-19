package com.gwn.xcbl.bl.mail;

import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.model.AppData;

public class EmailFromAddressFactory {
	
	public static EmailRecipient getNoReplyAddress() {
		String name = AppConstants.APP_NAME + " No-Reply";
		if (AppData.getInstance().isEnvProd()) {
			String address = AppData.getInstance().getEmailUsernameNoReply() + "@" + AppData.getInstance().getEmailServiceDomain();
			return new EmailRecipient(name, address);
		} else {
			String address = "mailgun@" + AppData.getInstance().getEmailServiceDomain();
			return new EmailRecipient(name, address);
		}
	}
}
