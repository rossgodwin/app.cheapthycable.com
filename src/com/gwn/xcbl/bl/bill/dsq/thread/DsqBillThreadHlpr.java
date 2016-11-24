package com.gwn.xcbl.bl.bill.dsq.thread;

import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiThread;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillThread;

public class DsqBillThreadHlpr {

	public static DsqBillThread create(long billId, DsqApiThread apiThread) {
		Bill bill = DAOFactory.getInstance().getBillDAO().findById(billId, true);
		
		DsqBillThread dbo = new DsqBillThread();
		dbo.setBill(bill);
		dbo.setDsqThreadId(apiThread.getId());
		update(dbo, apiThread);
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(dbo);
		
		return dbo;
	}
	
	public static void update(DsqBillThread billThread, DsqApiThread apiThread) {
		billThread.setDsqLikes(apiThread.getLikes());
		billThread.setDsqPosts(apiThread.getPosts());
	}
}
