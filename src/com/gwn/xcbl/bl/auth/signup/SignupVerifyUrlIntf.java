package com.gwn.xcbl.bl.auth.signup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.gwn.xcbl.data.hibernate.entity.User;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public interface SignupVerifyUrlIntf {

	public static final String URL = "srv/SignupVerify";
	
	public static final String PARAM_TOKEN = "token";
	
	public static class Util {
		
		public static List<NameValuePair> getParams(User user) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(PARAM_TOKEN, encrypt(user.getId())));
			return params;
		}

		public static String encrypt(long id) {
			Random rand = new Random((new Date()).getTime());
			
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] salt = new byte[8];
			rand.nextBytes(salt);
			return encoder.encode(salt) + encoder.encode(String.valueOf(id).getBytes());
		}
		
		public static Long decrypt(String encStr) {
			if (encStr.length() > 12) {
				String cipher = encStr.substring(12);
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					String idStr = new String(decoder.decodeBuffer(cipher));
					return Long.parseLong(idStr);
				} catch (IOException e) {
				}
			}
			return null;
		}
	}
}
