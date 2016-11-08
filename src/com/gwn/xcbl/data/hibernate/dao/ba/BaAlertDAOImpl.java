package com.gwn.xcbl.data.hibernate.dao.ba;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.gwn.xcbl.data.dao.ba.BaAlertDAO;
import com.gwn.xcbl.data.entity.ba.BaAlertTableMetadata;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.dao.GenericHibernateDAO;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.s.ba.BaAlertSqueryUtils;
import com.gwn.xcbl.data.shared.ILongId;

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
	
	public int countEmailAlertsByAccount(long accountId) {
		Query q = getSession().createQuery("select count(*) from " + BaAlert.class.getSimpleName() + " a where a.account.id = :accountId and a.receiveEmail = :receiveEmail");
		q.setParameter("accountId", accountId);
		q.setParameter("receiveEmail", true);
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<BaAlert> findAlertsToSend(LocalDateTime currentDate, Integer offset, Integer limit) {
		QueryComposite qc = findAlertsToSendSqlQuery(currentDate, true);
		SQLQuery q = getSession().createSQLQuery("select * " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		q.addEntity(BaAlert.class);
		List<BaAlert> r = q.list();
		return r;
	}
	
	private QueryComposite findAlertsToSendSqlQuery(LocalDateTime currentDate, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BaAlertTableMetadata.TABLE_NAME + " a where 1 = 1");
		BaAlertSqueryUtils.appendToSendCritr("a", qsb, params, currentDate);
		
		if (orderBy) {
			qsb.append(" ").append(new QueryOrderByBuilder().addKeyword().addOrderBy("a." + BaAlertTableMetadata.COL_ID).asString());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
}
