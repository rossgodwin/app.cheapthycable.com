package com.gwn.xcbl.bl.social.disqus.api;

public class DsqApiKeys {

	private String apiKey; // aka as public key

	private String apiSecret; // aka secret key

	private String accessToken;

	public DsqApiKeys() {
	}
	
	public DsqApiKeys(String apiKey, String apiSecret) {
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
	}
	
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiSecret() {
		return apiSecret;
	}

	public void setApiSecret(String apiSecret) {
		this.apiSecret = apiSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
