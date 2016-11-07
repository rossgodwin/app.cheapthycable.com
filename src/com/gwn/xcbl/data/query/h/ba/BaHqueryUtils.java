package com.gwn.xcbl.data.query.h.ba;

import java.time.LocalDateTime;
import java.util.Map;

import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaLastAlert;
import com.gwn.xcbl.data.hibernate.entity.ba.BaNextAlert;

public class BaHqueryUtils {

	/**
	 * 
	 * @param tableAlias - {@link Account}
	 * @param qsb
	 * @param params
	 * @param currentDate
	 */
	public static void appendAccountReceiveBillAlertsByDateCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, LocalDateTime currentDate) {
		qsb.append(" and (");
		{
			qsb.append(tableAlias).append(".billAlertReceiveFrequencyDays is null");
			qsb.append(" or not exists (");
			{
				qsb.append("from ").append(BaLastAlert.class.getSimpleName()).append(" la where la.account.id = ").append(tableAlias).append(".id");
			}
			qsb.append(")");
			qsb.append(" or exists (");
			{
				qsb.append("from ").append(BaNextAlert.class.getSimpleName()).append(" na where na.account.id = ").append(tableAlias).append(".id and :currentDate >= na.nextAlertDate");
				params.put("currentDate", currentDate);
			}
			qsb.append(")");
		}
		qsb.append(")");
	}
}
