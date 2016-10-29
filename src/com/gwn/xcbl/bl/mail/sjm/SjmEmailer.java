package com.gwn.xcbl.bl.mail.sjm;

import com.gwn.xcbl.bl.mail.data.model.Email;

public class SjmEmailer {

	public void sendEmail(Email email, boolean async) {
		org.simplejavamail.email.Email sEmail = new SjmEmailTransformer().transform(email);
		SjmMailerFactory.getMailer().sendMail(sEmail, async);
	}
}
