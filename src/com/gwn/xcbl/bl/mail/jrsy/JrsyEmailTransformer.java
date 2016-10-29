package com.gwn.xcbl.bl.mail.jrsy;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;

public class JrsyEmailTransformer implements Transformer<Email, FormDataMultiPart> {

	@Override
	public FormDataMultiPart transform(Email input) {
		JrsyEmailBuilder bldr = new JrsyEmailBuilder();
		bldr.addFromAddress(input.getFrom());
		bldr.addToAddress(input.getRecipients().get(0));
		bldr.setSubject(input.getSubject());
		bldr.setHtmlMsg(input.getHtmlMsg());
		
		EmailBodyPart logoBodyPart = input.getLogoBodyPart();
		if (logoBodyPart != null) {
			bldr.embedFile(logoBodyPart.getName(), logoBodyPart.getFileRealPath(), logoBodyPart.getMediaType());
		}
		
		return bldr.getFormData();
	}
}
