package com.gwn.xcbl.data.hibernate.dao;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.hibernate.Query;

public class DAOUtils {

	public static void applyParameters(Query query, Map<String, Object> params) {
		Iterator<Entry<String, Object>> it = params.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Object> param = it.next();
			query.setParameter(param.getKey(), param.getValue());
		}
	}
	
	public static void applyPaging(Query query, Integer offset, Integer limit) {
		if (offset != null && limit != null) {
			query.setFirstResult(offset);
			query.setMaxResults(limit);
		}
	}
	
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
