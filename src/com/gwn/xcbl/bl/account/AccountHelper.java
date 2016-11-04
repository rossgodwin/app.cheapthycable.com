package com.gwn.xcbl.bl.account;

import java.time.LocalDateTime;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.Account;

public class AccountHelper {

	public static Account createAccount() {
		Account account = new Account();
		account.setCreateDate(LocalDateTime.now());
		account.setReceiveLowerBillAlerts(false);
		HibernateUtil.getSessionFactory().getCurrentSession().save(account);
		return account;
	}
}
