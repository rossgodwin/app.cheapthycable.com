package com.gwn.xcbl.data.hibernate.dao.ba;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.ba.BaAlertDAO;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.dao.GenericHibernateDAO;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.h.AccountHqueryUtils;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.SortOrder;

public class BaAlertDAOImpl extends GenericHibernateDAO<BaAlert, ILongId> implements BaAlertDAO {

	@Override
	public BaAlert findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}

	@Override
	public BaAlert findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + BaAlert.class.getSimpleName() + " a where a.id = :id");
		q.setParameter("id", id);
		BaAlert r = (BaAlert) q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BaAlert> findSubscribedAlertsByAccount(long accountId, Integer offset, Integer limit) {
		QueryComposite qc = findSubscribedAlertsByAccountQuery(accountId, true);
		Query q = getSession().createQuery(qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		List<BaAlert> r = q.list();
		return r;
	}
	
	@Override
	public int countSubscribedAlertsByAccount(long accountId) {
		QueryComposite qc = findSubscribedAlertsByAccountQuery(accountId, true);
		Query q = getSession().createQuery("select count(*) " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
	
	private QueryComposite findSubscribedAlertsByAccountQuery(long accountId, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BaAlert.class.getSimpleName() + " a where 1 = 1");
		AccountHqueryUtils.appendIdEqualsCritr("a.account", qsb, params, accountId);
		qsb.append(" and a.unsubscribed = :unsubscribed");
		params.put("unsubscribed", false);
		
		if (orderBy) {
			qsb.append(" ").append(new QueryOrderByBuilder().addKeyword().addOrderBy("a.id", SortOrder.DESC).asString());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
	
	public int countEmailAlertsByAccount(long accountId) {
		Query q = getSession().createQuery("select count(*) from " + BaAlert.class.getSimpleName() + " a where a.account.id = :accountId and a.receiveEmail = :receiveEmail");
		q.setParameter("accountId", accountId);
		q.setParameter("receiveEmail", true);
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
}
