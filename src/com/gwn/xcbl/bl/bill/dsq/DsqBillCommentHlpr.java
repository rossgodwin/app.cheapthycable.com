package com.gwn.xcbl.bl.bill.dsq;

import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillComment;

public class DsqBillCommentHlpr {

	public static DsqBillComment create(long accountId, long billId, long dsqCommentId) {
		Account account = DAOFactory.getInstance().getAccountDAO().findById(accountId, true);
		Bill bill = DAOFactory.getInstance().getBillDAO().findById(billId, true);
		
		DsqBillComment dbo = new DsqBillComment();
		dbo.setAccount(account);
		dbo.setBill(bill);
		dbo.setDsqCommentId(dsqCommentId);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		
		return dbo;
	}
}
