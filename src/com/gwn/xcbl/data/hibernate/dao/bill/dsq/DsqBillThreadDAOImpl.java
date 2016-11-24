package com.gwn.xcbl.data.hibernate.dao.bill.dsq;

import org.hibernate.Query;

import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillThread;

public class DsqBillThreadDAOImpl extends BaseDAO {

	public DsqBillThread findByDsqThread(long dsqThreadId) {
		Query q = getSession().createQuery("from " + DsqBillThread.class.getSimpleName() + " c where c.dsqThreadId = :dsqThreadId");
		q.setParameter("dsqThreadId", dsqThreadId);
		DsqBillThread r = (DsqBillThread) q.uniqueResult();
		return r;
	}
}
