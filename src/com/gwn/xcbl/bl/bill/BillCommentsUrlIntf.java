package com.gwn.xcbl.bl.bill;

import java.net.URISyntaxException;

import org.hashids.Hashids;

public interface BillCommentsUrlIntf {

	/**
	 * https://regex101.com/
	 * \\/[a-zA-Z]+\\/bill\\/\\d+\\/comments
	 * 		i.e. /app/bill/1001/comments
	 */
	public static final String URL_REGEX_PATTERN = "\\/[a-zA-Z]+\\/bill\\/[a-zA-Z0-9]+\\/comments";
	
	public static class Utils {
		public static String getUrl(String baseUrl, long billId) throws URISyntaxException {
			String encBillId = new Hashids().encode(billId);
			StringBuilder sb = new StringBuilder();
			sb.append(baseUrl).append("/app/bill/").append(encBillId).append("/comments");
			String rslt = sb.toString();
			return rslt;
		}
	}
}
