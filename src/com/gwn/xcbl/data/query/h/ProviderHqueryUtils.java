package com.gwn.xcbl.data.query.h;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.data.query.QueryUtils;

public class ProviderHqueryUtils {

	public static void appendNameLikeCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String name) {
		if (StringUtils.isNotEmpty(name)) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and lower(").append(tableAlias).append(".name) like :").append(param);
			params.put(param, "%" + name + "%");
		}
	}
}
