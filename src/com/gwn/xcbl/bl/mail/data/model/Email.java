package com.gwn.xcbl.bl.mail.data.model;

import java.util.ArrayList;
import java.util.List;

public class Email {

	private EmailRecipient from;
	
	private List<EmailRecipient> recipients = new ArrayList<EmailRecipient>();
	
	private String subject;
	
	private String htmlMsg;
	
	private EmailBodyPart logoBodyPart;

	public EmailRecipient getFrom() {
		return from;
	}

	public void setFrom(EmailRecipient from) {
		this.from = from;
	}

	public List<EmailRecipient> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<EmailRecipient> recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getHtmlMsg() {
		return htmlMsg;
	}

	public void setHtmlMsg(String htmlMsg) {
		this.htmlMsg = htmlMsg;
	}

	public EmailBodyPart getLogoBodyPart() {
		return logoBodyPart;
	}

	public void setLogoBodyPart(EmailBodyPart logoBodyPart) {
		this.logoBodyPart = logoBodyPart;
	}
}
