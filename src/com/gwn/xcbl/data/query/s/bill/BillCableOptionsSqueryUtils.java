package com.gwn.xcbl.data.query.s.bill;

import java.util.Map;

import com.gwn.xcbl.data.entity.BillCableOptionsTableMetadata;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.query.QueryUtils;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;

public class BillCableOptionsSqueryUtils {

	public static void appendCableOptionCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BillSearchCritrDTO critr) {
		if ((critr.getCableService() != null && critr.getCableService()) && (critr.getCableOptionBoxCount() != null || critr.getCableOptionDvrCount() != null || critr.getCableOptionSpecialChannels() != null)) {
			qsb.append(" and exists (");
			{
				qsb.append("select 1");
				qsb.append(" from ").append(BillCableOptionsTableMetadata.TABLE_NAME).append(" c");
				qsb.append(" where c.").append(BillCableOptionsTableMetadata.COL_BILL_ID).append(" = ").append(tableAlias).append(".").append(BillTableMetadata.COL_ID);
				appendBoxCountCritr("c", qsb, params, critr.getCableOptionBoxCount());
				appendDvrCountCritr("c", qsb, params, critr.getCableOptionDvrCount());
				appendSpecialChannelsCritr("c", qsb, params, critr.getCableOptionSpecialChannels());
			}
			qsb.append(")");
		}
	}
	
	public static void appendBoxCountCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Integer count) {
		if (count != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillCableOptionsTableMetadata.COL_BOX_COUNT).append(" = :").append(param);
			params.put(param, count);
		}
	}
	
	public static void appendDvrCountCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Integer count) {
		if (count != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillCableOptionsTableMetadata.COL_DVR_COUNT).append(" = :").append(param);
			params.put(param, count);
		}
	}
	
	public static void appendSpecialChannelsCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		if (value != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillCableOptionsTableMetadata.COL_SPECIAL_CHANNELS).append(" = :").append(param);
			params.put(param, value);
		}
	}
}
