package com.gwn.xcbl.bl.google.recaptcha;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

public class RecaptchaApiResourceSiteVerify {

	public String getSiteVerifyUrl(String secretKey, String token) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("secret", secretKey));
		params.add(new BasicNameValuePair("response", token));
		
		try {
			URIBuilder bldr = new URIBuilder("https://www.google.com/recaptcha/api/siteverify");
			bldr.setParameters(params);
			String url = bldr.toString();
			return url;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
