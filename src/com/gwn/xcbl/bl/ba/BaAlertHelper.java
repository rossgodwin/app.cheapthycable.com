package com.gwn.xcbl.bl.ba;

import java.math.BigDecimal;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;

public class BaAlertHelper {

	public static BaAlert addDefaultAlert(Account account) {
		BaAlert dbo = new BaAlert();
		dbo.setAccount(account);
		dbo.setReceiveEmail(true);
		dbo.setReceiveEmailFrequencyDays(14);
		dbo.setCritrAmountBelow(new BigDecimal(25));
		dbo.setCritrMileRadius(50.0);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		
		return dbo;
	}
}
