package com.gwn.xcbl.bl.ba;

import java.time.LocalDateTime;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlertSentLog;

public class BaAlertSentLogHelper {

	public static BaAlertSentLog logSentAlert(BaAlert alert) {
		BaAlertSentLog dbo = new BaAlertSentLog();
		dbo.setAlert(alert);
		dbo.setAlertedDate(LocalDateTime.now());
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		
		return dbo;
	}
}
