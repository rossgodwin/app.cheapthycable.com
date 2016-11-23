package com.gwn.xcbl.data.hibernate.dao.bill.dsq;

import java.util.List;

import org.hibernate.Query;

import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillPost;

public class DsqBillPostDAOImpl extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<DsqBillPost> findByBill(long billId) {
		Query q = getSession().createQuery("from " + DsqBillPost.class.getSimpleName() + " c where c.bill.id = :billId");
		q.setParameter("billId", billId);
		List<DsqBillPost> r = q.list();
		return r;
	}
	
	public int countByBill(long billId) {
		Query q = getSession().createQuery("select count(*) from " + DsqBillPost.class.getSimpleName() + " c where c.bill.id = :billId");
		q.setParameter("billId", billId);
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
}
