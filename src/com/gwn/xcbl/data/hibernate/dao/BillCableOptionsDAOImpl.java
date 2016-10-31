package com.gwn.xcbl.data.hibernate.dao;

import java.util.Map;

import com.gwn.xcbl.data.entity.BillCableOptionsTableMetadata;

public class BillCableOptionsDAOImpl {

	public static void appendSqlDvrCountCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Integer count) {
		if (count != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillCableOptionsTableMetadata.COL_DVR_COUNT).append(" = :").append(param);
			params.put(param, count);
		}
	}
	
	public static void appendSqlBoxCountCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Integer count) {
		if (count != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillCableOptionsTableMetadata.COL_BOX_COUNT).append(" = :").append(param);
			params.put(param, count);
		}
	}
	
	public static void appendSqlSpecialChannelsCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		if (value != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillCableOptionsTableMetadata.COL_SPECIAL_CHANNELS).append(" = :").append(param);
			params.put(param, value);
		}
	}
}
