package com.gwn.xcbl.data.hibernate.dao.bill.dsq;

import org.hibernate.Query;

import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillComment;

public class DsqBillCommentDAOImpl extends BaseDAO {

	public int countByBill(long billId) {
		Query q = getSession().createQuery("select count(*) from " + DsqBillComment.class.getSimpleName() + " c where c.bill.id = :billId");
		q.setParameter("billId", billId);
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
}
