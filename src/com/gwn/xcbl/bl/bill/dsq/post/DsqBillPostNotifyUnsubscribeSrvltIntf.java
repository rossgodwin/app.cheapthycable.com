package com.gwn.xcbl.bl.bill.dsq.post;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.gwn.xcbl.bl.EncryptionUtils;

public interface DsqBillPostNotifyUnsubscribeSrvltIntf {

	public static final String URL = "srv/DsqBillPostNotifyUnsubscribe";
	
	public static final String PARAM_TOKEN = "token";
	
	public static class Util {
		
		public static List<NameValuePair> getParams(long userId) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(PARAM_TOKEN, encrypt(userId)));
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
