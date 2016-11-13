package com.gwn.xcbl.data.query.s.ba;

import java.time.LocalDateTime;
import java.util.Map;

import com.gwn.xcbl.data.entity.ba.BaAlertSentLogTableMetadata;
import com.gwn.xcbl.data.entity.ba.BaAlertTableMetadata;
import com.gwn.xcbl.data.entity.ba.BaVwAlertSentLogLastTableMetadata;

public class BaAlertSqueryUtils {

	public static void appendToSendCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, LocalDateTime currentDate) {
		qsb.append(" and ").append(tableAlias).append(".").append(BaAlertTableMetadata.COL_UNSUBSCRIBED).append(" = :unsubscribed");
		params.put("unsubscribed", false);
		
		qsb.append(" and (");
		{
			qsb.append("not exists (");
			{
				qsb.append("select 1 from ").append(BaAlertSentLogTableMetadata.TABLE_NAME).append(" s where s.").append(BaAlertSentLogTableMetadata.COL_ALERT_ID).append(" = ").append(tableAlias).append(".").append(BaAlertTableMetadata.COL_ID);
			}
			qsb.append(")");
			
			qsb.append(" or exists (");
			{
				qsb.append("select 1 from ").append(BaVwAlertSentLogLastTableMetadata.TABLE_NAME).append(" s where s.").append(BaAlertSentLogTableMetadata.COL_ALERT_ID).append(" = ").append(tableAlias).append(".").append(BaAlertTableMetadata.COL_ID).append(""
						+ " and :currentDate >= s.").append(BaAlertSentLogTableMetadata.COL_ALERTED_DATE).append(" + interval ").append(tableAlias).append(".").append(BaAlertTableMetadata.COL_RECEIVE_EMAIL_FREQUENCY_DAYS).append(" day");
				params.put("currentDate", currentDate.toString());
			}
			qsb.append(")");
		}
		qsb.append(")");
	}
}
