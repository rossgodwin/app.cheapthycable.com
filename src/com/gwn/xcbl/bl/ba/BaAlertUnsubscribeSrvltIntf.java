package com.gwn.xcbl.bl.ba;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.gwn.xcbl.bl.EncryptionUtils;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;

public interface BaAlertUnsubscribeSrvltIntf {

	public static final String URL = "srv/BaAlertUnsubscribe";
	
	public static final String PARAM_TOKEN = "token";
	
	public static class Util {
		
		public static List<NameValuePair> getParams(BaAlert alert) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(PARAM_TOKEN, encrypt(alert.getId())));
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
