package com.gwn.xcbl.data.hibernate.dao.ba;

import org.hibernate.Query;

import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.entity.ba.BaLastAlert;

public class BaLastAlertDAOImpl extends BaseDAO {

	public BaLastAlert findByAccountId(long accountId) {
		Query q = getSession().createQuery("from " + BaLastAlert.class.getSimpleName() + " a where a.account.id = :accountId");
		q.setParameter("accountId", accountId);
		BaLastAlert r = (BaLastAlert) q.uniqueResult();
		return r;
	}
}
