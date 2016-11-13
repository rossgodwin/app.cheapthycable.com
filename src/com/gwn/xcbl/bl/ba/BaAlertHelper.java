package com.gwn.xcbl.bl.ba;

import java.math.BigDecimal;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.shared.ba.BaAlertDTO;

public class BaAlertHelper {

	public static BaAlert addDefaultAlert(Account account) {
		BaAlert dbo = new BaAlert();
		dbo.setAccount(account);
		dbo.setReceiveEmailFrequencyDays(14);
		dbo.setCritrAmountBelow(new BigDecimal(25));
		dbo.setCritrMileRadius(50.0);
		dbo.setUnsubscribed(false);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		
		return dbo;
	}
	
	public static BaAlert save(Account account, BaAlertDTO dto) {
		BaAlert dbo = new BaAlert();
		dbo.setAccount(account);
		dbo.setCritrAmountBelow(dto.getCritrAmountBelow());
		dbo.setCritrMileRadius(dto.getCritrMileRadius());
		dbo.setReceiveEmailFrequencyDays(dto.getReceiveEmailFrequencyDays());
		dbo.setUnsubscribed(dto.isUnsubscribed());
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		dto.setId(dbo.getId());
		
		return dbo;
	}
}
