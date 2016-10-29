package com.gwn.xcbl.bl.mail.sjm;

import javax.activation.FileDataSource;

import org.apache.commons.collections4.Transformer;
import org.simplejavamail.email.EmailBuilder;

import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;

public class SjmEmailTransformer implements Transformer<Email, org.simplejavamail.email.Email> {

	@Override
	public org.simplejavamail.email.Email transform(Email input) {
		EmailBuilder builder = new EmailBuilder();
		builder.from(input.getFrom().getName(), input.getFrom().getAddress());
		EmailRecipient to = input.getRecipients().get(0);
		builder.to(to.getName(), to.getAddress());
		builder.subject(input.getSubject());
		builder.textHTML(input.getHtmlMsg());
		
		org.simplejavamail.email.Email email = builder.build();
		
		SjmEmailUtils.setDefaultFromAddress(email);
		EmailBodyPart logoBodyPart = input.getLogoBodyPart();
		if (logoBodyPart != null) {
			email.addEmbeddedImage(logoBodyPart.getName(), new FileDataSource(logoBodyPart.getFileRealPath()));
		}
		
		return email;
	}
}
