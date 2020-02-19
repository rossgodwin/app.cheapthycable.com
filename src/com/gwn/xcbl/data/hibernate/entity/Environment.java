package com.gwn.xcbl.data.hibernate.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gwn.xcbl.data.model.EnvironmentType;

@Entity
@Table(name = "environment")
public class Environment {

	@Id
	@Enumerated(EnumType.STRING)
	@Column(name = "type", nullable = false)
	private EnvironmentType type;
	
	@Column(name = "domain_url", nullable = false)
	private String domainUrl;
	
	@Column(name = "bill_alert_enabled", nullable = false)
	private boolean billAlertEnabled;
	
	@Column(name = "disqus_shortname", nullable = false)
	private String disqusShortname;
	
	@Column(name = "disqus_api_url")
	private String disqusApiUrl;
	
	@Column(name = "disqus_api_key")
	private String disqusApiKey;
	
	@Column(name = "disqus_api_secret")
	private String disqusApiSecret;
	
	@Column(name = "email_service_domain")
	private String emailServiceDomain;
	
	@Column(name = "email_service_api_url")
	private String emailServiceApiUrl;
	
	@Column(name = "email_service_api_key")
	private String emailServiceApiKey;
	
	@Column(name = "email_username_no_reply")
	private String emailUsernameNoReply;

	public EnvironmentType getType() {
		return type;
	}

	public void setType(EnvironmentType type) {
		this.type = type;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public boolean isBillAlertEnabled() {
		return billAlertEnabled;
	}

	public void setBillAlertEnabled(boolean billAlertEnabled) {
		this.billAlertEnabled = billAlertEnabled;
	}

	public String getDisqusShortname() {
		return disqusShortname;
	}

	public void setDisqusShortname(String disqusShortname) {
		this.disqusShortname = disqusShortname;
	}

	public String getDisqusApiUrl() {
		return disqusApiUrl;
	}

	public void setDisqusApiUrl(String disqusApiUrl) {
		this.disqusApiUrl = disqusApiUrl;
	}

	public String getDisqusApiKey() {
		return disqusApiKey;
	}

	public void setDisqusApiKey(String disqusApiKey) {
		this.disqusApiKey = disqusApiKey;
	}

	public String getDisqusApiSecret() {
		return disqusApiSecret;
	}

	public void setDisqusApiSecret(String disqusApiSecret) {
		this.disqusApiSecret = disqusApiSecret;
	}

	public String getEmailServiceDomain() {
		return emailServiceDomain;
	}

	public void setEmailServiceDomain(String emailServiceDomain) {
		this.emailServiceDomain = emailServiceDomain;
	}

	public String getEmailServiceApiUrl() {
		return emailServiceApiUrl;
	}

	public void setEmailServiceApiUrl(String emailServiceApiUrl) {
		this.emailServiceApiUrl = emailServiceApiUrl;
	}

	public String getEmailServiceApiKey() {
		return emailServiceApiKey;
	}

	public void setEmailServiceApiKey(String emailServiceApiKey) {
		this.emailServiceApiKey = emailServiceApiKey;
	}

	public String getEmailUsernameNoReply() {
		return emailUsernameNoReply;
	}

	public void setEmailUsernameNoReply(String emailUsernameNoReply) {
		this.emailUsernameNoReply = emailUsernameNoReply;
	}
}
