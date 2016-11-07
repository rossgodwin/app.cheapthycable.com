package com.gwn.xcbl.data.query;

import java.util.Map;
import java.util.Random;

public class QueryUtils {

	public static String generateRandomUniqueParam(Map<String, Object> params) {
		while (true) {
			String param = generateRandomParam();
			if (params.get(param) == null) {
				return param;
			}
		}
	}
	
	public static String generateRandomParam() {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}
}
