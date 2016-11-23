package com.gwn.xcbl.bl.bill.dsq;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillPost;

public class DsqBillPostHlpr {

	public static DsqBillPost create(long accountId, long billId, long dsqPostId) {
		Account account = DAOFactory.getInstance().getAccountDAO().findById(accountId, true);
		Bill bill = DAOFactory.getInstance().getBillDAO().findById(billId, true);
		
		DsqBillPost dbo = new DsqBillPost();
		dbo.setAccount(account);
		dbo.setBill(bill);
		dbo.setDsqPostId(dsqPostId);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		
		return dbo;
	}
}
