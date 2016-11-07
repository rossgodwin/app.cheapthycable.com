package com.gwn.xcbl.data.hibernate.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.gwn.xcbl.bl.CurrencyUtils;
import com.gwn.xcbl.data.dao.BillDAO;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.CurrentBill;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.model.bill.BillExplorerStats;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.h.AccountHqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillCableOptionsSqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillSqueryUtils;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.SortOrder;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Bill> findBillsByCritr(BillSearchCritrDTO critr, Integer offset, Integer limit) {
		QueryComposite qc = findBillsByCritrSqlQuery(critr, true);
		SQLQuery q = getSession().createSQLQuery("select * " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		q.addEntity(Bill.class);
		List<Bill> r = q.list();
		return r;
	}
	
	@Override
	public int countBillsByCritr(BillSearchCritrDTO critr) {
		QueryComposite qc = findBillsByCritrSqlQuery(critr, false);
		SQLQuery q = getSession().createSQLQuery("select count(*) " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
	
	private QueryComposite findBillsByCritrSqlQuery(BillSearchCritrDTO critr, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BillTableMetadata.TABLE_NAME + " b where 1 = 1");
		
		BillSqueryUtils.appendZipCodeRadiusCritr("b", qsb, critr.getZipCode(), critr.getMileRadius());
		BillSqueryUtils.appendTotalAmountEqualsCritr("b", qsb, params, critr.getExactTotalAmount());
		BillSqueryUtils.appendProviderCritr("b", qsb, params, critr.getProviderId());
		BillSqueryUtils.appendProviderNameMatchCritr("b", qsb, params, critr.getMatchProviderName());
		BillSqueryUtils.appendServicesCritr("b", qsb, params, critr);
		BillCableOptionsSqueryUtils.appendCableOptionCritr("b", qsb, params, critr);
		
		if (orderBy) {
			BillSqueryUtils.appendOrderBy("b", qsb, critr.getSorts());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
	
	public BillExplorerStats getBillExplorerStatsByCritr(BillSearchCritrDTO critr) {
		StringBuilder selectQsb = new StringBuilder();
		selectQsb.append("select count(1),");
		selectQsb.append(" count(distinct ").append(BillTableMetadata.COL_GEO_ZIP_CODE_ID).append("),");
		selectQsb.append(" min(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append("),");
		selectQsb.append(" avg(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append("),");
		selectQsb.append(" max(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append(")");
		
		StringBuilder qsb = new StringBuilder();
		qsb.append(selectQsb.toString());
		
		QueryComposite qc = findBillsByCritrSqlQuery(critr, false);
		qsb.append(" ").append(qc.getQueryString().toString());
		
		SQLQuery q = HibernateUtil.getSessionFactory().getCurrentSession().createSQLQuery(qsb.toString());
		DAOUtils.applyParameters(q, qc.getParams());
		
		Object[] qr = (Object[])q.uniqueResult();
		
		BillExplorerStats r = new BillExplorerStats();
		int count = ((Number)qr[0]).intValue();
		r.setCountOfBills(count);
		if (count > 0) {
			r.setCountOfZipCodes(((Number)qr[1]).intValue());
			r.setLowestTotalAmount(CurrencyUtils.roundCurrency((BigDecimal)qr[2]));
			r.setAverageTotalAmount(CurrencyUtils.roundCurrency((BigDecimal)qr[3]));
			r.setHighestTotalAmount(CurrencyUtils.roundCurrency((BigDecimal)qr[4]));
		}
		
		return r;
	}
}
