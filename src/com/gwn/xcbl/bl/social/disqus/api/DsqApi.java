package com.gwn.xcbl.bl.social.disqus.api;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

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
	
	public <M> M call(String url, Type type) {
		HttpGet get = new HttpGet(url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			CloseableHttpResponse httpResponse = client.execute(get);
			
			int status = httpResponse.getStatusLine().getStatusCode();
			if ((status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) || status == HttpStatus.SC_BAD_REQUEST) {
				HttpEntity entity = httpResponse.getEntity();
				String json = EntityUtils.toString(entity);
				M response = new Gson().fromJson(json, type);
				return response;
			} else {
				throw new ClientProtocolException("Unexpected response status: " + status);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
