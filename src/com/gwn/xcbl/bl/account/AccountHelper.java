package com.gwn.xcbl.bl.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.Account;

public class AccountHelper {

	public static Account createAccount() {
		Account account = new Account();
		account.setCreateDate(LocalDateTime.now());
		account.setBillAlertReceive(false);
		HibernateUtil.getSessionFactory().getCurrentSession().save(account);
		return account;
	}
	
	public static void setReceiveBillAlerts(Account account) {
		account.setBillAlertReceive(true);
		account.setBillAlertReceiveFrequencyDays(14);
		account.setBillAlertReceiveAmountBelow(new BigDecimal(25));
		account.setBillAlertReceiveMileRadius(50.0);
	}
}
