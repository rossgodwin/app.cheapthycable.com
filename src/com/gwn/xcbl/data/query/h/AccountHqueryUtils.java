package com.gwn.xcbl.data.query.h;

import java.util.Map;

import com.gwn.xcbl.data.query.QueryUtils;

public class AccountHqueryUtils {

	public static void appendIdEqualsCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, long accountId) {
		String param = QueryUtils.generateRandomUniqueParam(params);
		qsb.append(" and ").append(tableAlias).append(".id = :").append(param);
		params.put(param, accountId);
	}
	
	public static void appendBillAlertReceiveCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean receiveAlerts) {
		if (receiveAlerts != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".billAlertReceive = :").append(param);
			params.put(param, receiveAlerts);
		}
	}
}
