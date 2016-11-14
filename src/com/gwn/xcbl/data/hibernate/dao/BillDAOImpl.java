package com.gwn.xcbl.data.hibernate.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.gwn.xcbl.data.dao.BillDAO;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.CurrentBill;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.h.AccountHqueryUtils;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.SortOrder;

public class BillDAOImpl extends GenericHibernateDAO<Bill, ILongId> implements BillDAO {

	@Override
	public Bill findByILongId(ILongId id, boolean readOnly) {
		return findById(id.getId(), readOnly);
	}

	@Override
	public Bill findById(Long id, boolean readOnly) {
		Query q = getSession().createQuery("from " + Bill.class.getSimpleName() + " b where b.id = :id");
		q.setParameter("id", id);
		Bill r = (Bill) q.uniqueResult();
		return r;
	}
	
	@Override
	public Bill findCurrentBill(long accountId) {
		Query q = getSession().createQuery("select b.bill from " + CurrentBill.class.getSimpleName() + " b where b.account.id = :accountId");
		q.setParameter("accountId", accountId);
		Bill r = (Bill) q.uniqueResult();
		return r;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bill> findAccountBills(long accountId, Integer offset, Integer limit) {
		QueryComposite qc = findAccountBillsQuery(accountId, true);
		Query q = getSession().createQuery(qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		List<Bill> r = q.list();
		return r;
	}
	
	private QueryComposite findAccountBillsQuery(long accountId, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + Bill.class.getSimpleName() + " b where 1 = 1");
		AccountHqueryUtils.appendIdEqualsCritr("b.account", qsb, params, accountId);
		
		if (orderBy) {
			qsb.append(" ").append(new QueryOrderByBuilder().addKeyword().addOrderBy("b.validDate", SortOrder.DESC).addOrderBy("b.id", SortOrder.DESC).asString());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
}
