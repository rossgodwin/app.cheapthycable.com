package com.gwn.xcbl.bl.mail.sjm;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import com.gwn.xcbl.bl.mail.EmailConstants;

public class SjmMailerFactory {

	/**
	 * https://www.google.com/settings/security/lesssecureapps
	 * http://stackoverflow.com/questions/16115453/javamail-could-not-convert-socket-to-tls-gmail
	 * 
	 * @return
	 */
	public static Mailer getMailer() {
		final String user = EmailConstants.PROPERTY_FROM_ADDRESS;
		final String pwd = EmailConstants.PROPERTY_FROM_PWD;
		
		Mailer m = new Mailer("smtp.gmail.com", 25, user, pwd, TransportStrategy.SMTP_TLS);
//		Mailer m = new Mailer("smtp.gmail.com", 587, user, pwd, TransportStrategy.SMTP_TLS);
//		Mailer m = new Mailer("smtp.gmail.com", 465, user, pwd, TransportStrategy.SMTP_SSL);
		
		return m;
	}
}
