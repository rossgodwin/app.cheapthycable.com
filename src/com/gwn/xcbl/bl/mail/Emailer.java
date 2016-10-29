package com.gwn.xcbl.bl.mail;

import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.jrsy.JrsyEmailConfigurationFactory;
import com.gwn.xcbl.bl.mail.jrsy.JrsyEmailer;

public class Emailer {

	public static void sendEmail(Email email) {
		new JrsyEmailer(JrsyEmailConfigurationFactory.getConfig()).sendEmail(email);
//		new SjmEmailer().sendEmail(email);
	}
}
