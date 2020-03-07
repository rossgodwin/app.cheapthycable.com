package com.gwn.xcbl.bl.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static String callGet(String url) {
		HttpGet get = new HttpGet(url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			CloseableHttpResponse httpResponse = client.execute(get);
			
			int status = httpResponse.getStatusLine().getStatusCode();
			if ((status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) || status == HttpStatus.SC_BAD_REQUEST) {
				HttpEntity entity = httpResponse.getEntity();
				String s = EntityUtils.toString(entity);
				return s;
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
	
	public static String callPost(String url) {
		HttpPost post = new HttpPost(url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		try {
			CloseableHttpResponse httpResponse = client.execute(post);
			
			int status = httpResponse.getStatusLine().getStatusCode();
			if ((status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) || status == HttpStatus.SC_BAD_REQUEST) {
				HttpEntity entity = httpResponse.getEntity();
				String s = EntityUtils.toString(entity);
				return s;
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
