package com.gwn.xcbl.bl.social.disqus.api;

import java.util.Collection;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class DsqApi {

	private String apiUrl;
	
	private DsqApiKeys keys;
	
	public DsqApi() {
	}
	
	public DsqApi(String apiUrl, DsqApiKeys keys) {
		this.apiUrl = apiUrl;
		this.keys = keys;
	}
	
	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public DsqApiKeys getKeys() {
		return keys;
	}

	public void setKeys(DsqApiKeys keys) {
		this.keys = keys;
	}
	
	public void addApiKeysParams(Collection<NameValuePair> params) {
		params.add(new BasicNameValuePair("api_key", keys.getApiKey()));
		params.add(new BasicNameValuePair("api_secret", keys.getApiSecret()));
	}
}
