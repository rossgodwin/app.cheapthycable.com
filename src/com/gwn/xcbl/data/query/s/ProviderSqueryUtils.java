package com.gwn.xcbl.data.query.s;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.data.entity.ProviderTableMetadata;
import com.gwn.xcbl.data.query.QueryUtils;

public class ProviderSqueryUtils {

	public static void appendNameLikeCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String name) {
		if (StringUtils.isNotEmpty(name)) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and lower(").append(tableAlias).append(".").append(ProviderTableMetadata.COL_NAME).append(") like :").append(param);
			params.put(param, "%" + name + "%");
		}
	}
}
