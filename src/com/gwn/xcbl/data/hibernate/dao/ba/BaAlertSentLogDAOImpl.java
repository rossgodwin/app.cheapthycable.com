package com.gwn.xcbl.data.hibernate.dao.ba;

import java.util.List;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.ba.BaAlertSentLogDAO;
import com.gwn.xcbl.data.hibernate.dao.GenericHibernateDAO;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlertSentLog;
import com.gwn.xcbl.data.shared.ILongId;

public class BaAlertSentLogDAOImpl extends GenericHibernateDAO<BaAlertSentLog, ILongId> implements BaAlertSentLogDAO {

	@Override
	public BaAlertSentLog findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}

	@Override
	public BaAlertSentLog findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + BaAlertSentLog.class.getSimpleName() + " a where a.id = :id");
		q.setParameter("id", id);
		BaAlertSentLog r = (BaAlertSentLog) q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	public BaAlertSentLog findLastSentByAlert(long alertId) {
		Query q = getSession().createQuery("from " + BaAlertSentLog.class.getSimpleName() + " a where a.alert.id = :alertId"
				+ " and a.alertedDate = (select max(alertedDate) from " + BaAlertSentLog.class.getSimpleName() + " a1 where a1.alert.id = :alertId)"
						+ " order by a.id desc");
		q.setParameter("alertId", alertId);
		List<BaAlertSentLog> l = q.list();
		if (l.size() > 0) {
			BaAlertSentLog r = l.get(0);
			return r;
		}
		return null;
	}
}
