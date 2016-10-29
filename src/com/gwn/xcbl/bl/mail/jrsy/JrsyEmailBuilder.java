package com.gwn.xcbl.bl.mail.jrsy;

import javax.activation.FileDataSource;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class JrsyEmailBuilder {

	private FormDataMultiPart formData = new FormDataMultiPart();
	
	public JrsyEmailBuilder addFromAddress(EmailRecipient recipent) {
		return addFromAddress(recipientToString(recipent));
	}
	
	public JrsyEmailBuilder addFromAddress(String address) {
		formData.field("from", address);
		return this;
	}
	
	public JrsyEmailBuilder addToAddress(EmailRecipient recipent) {
		return addToAddress(recipientToString(recipent));
	}
	
	public JrsyEmailBuilder addToAddress(String address) {
		formData.field("to", address);
		return this;
	}
	
	public JrsyEmailBuilder setSubject(String subject) {
		formData.field("subject", subject);
		return this;
	}
	
	public JrsyEmailBuilder setHtmlMsg(String html) {
		formData.field("html", html);
		return this;
	}
	
	public void embedFile(String name, String fileRealPath, MediaType mediaType) {
		FileDataSource dataSrc = new FileDataSource(fileRealPath);
		FileDataBodyPart part = new FileDataBodyPart(name, dataSrc.getFile(), mediaType);
		formData.bodyPart(part);
	}
	
	private static String recipientToString(EmailRecipient recipient) {
		StringBuilder sb = new StringBuilder();
		boolean nameAdded = false;
		if (StringUtils.isNotEmpty(recipient.getName())) {
			sb.append(recipient.getName());
			nameAdded = true;
		}
		if (nameAdded) {
			sb.append(" <");
		}
		sb.append(recipient.getAddress());
		if (nameAdded) {
			sb.append(">");
		}
		return sb.toString();
	}
	
	public FormDataMultiPart getFormData() {
		return formData;
	}
}
