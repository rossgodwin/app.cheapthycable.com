package com.gwn.xcbl.bl.auth.signup;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.gwn.xcbl.bl.EncryptionUtils;
import com.gwn.xcbl.data.hibernate.entity.User;

public interface SignupVerifySrvltIntf {

	public static final String URL = "srv/SignupVerify";
	
	public static final String PARAM_TOKEN = "token";
	
	public static class Util {
		
		public static List<NameValuePair> getParams(User user) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(PARAM_TOKEN, encrypt(user.getId())));
			return params;
		}

		public static String encrypt(long id) {
			return EncryptionUtils.simpleLongEncrypt(id);
		}
		
		public static Long decrypt(String encStr) {
			return EncryptionUtils.simpleLongDecrypt(encStr);
		}
	}
}
