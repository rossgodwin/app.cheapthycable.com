package com.gwn.xcbl.data.hibernate.dao.be;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.gwn.xcbl.bl.CurrencyUtils;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.model.be.BeStats;
import com.gwn.xcbl.data.query.s.bill.BillCableOptionsSqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillSqueryUtils;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;

public class BeDAOImpl extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<Bill> findBeBillsByCritr(long accountId, BillSearchCritrDTO critr, Integer offset, Integer limit) {
		QueryComposite qc = findBeBillsByCritrSqlQuery(accountId, critr, true);
		SQLQuery q = getSession().createSQLQuery("select * " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		q.addEntity(Bill.class);
		List<Bill> r = q.list();
		return r;
	}
	
	public int countBeBillsByCritr(long accountId, BillSearchCritrDTO critr) {
		QueryComposite qc = findBeBillsByCritrSqlQuery(accountId, critr, false);
		SQLQuery q = getSession().createSQLQuery("select count(*) " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		Number count = (Number)q.uniqueResult();
		return count.intValue();
	}
	
	private QueryComposite findBeBillsByCritrSqlQuery(long accountId, BillSearchCritrDTO critr, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BillTableMetadata.TABLE_NAME + " b where 1 = 1");
		
		BillSqueryUtils.appendAccountIdNotEqualsCritr("b", qsb, params, accountId);
//		BillSqueryUtils.appendCurrentBillCritr("b", qsb);
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
	
	public BeStats getBeStatsByCritr(long accountId, BillSearchCritrDTO critr) {
		StringBuilder selectQsb = new StringBuilder();
		selectQsb.append("select count(1),");
		selectQsb.append(" count(distinct ").append(BillTableMetadata.COL_GEO_ZIP_CODE_ID).append("),");
		selectQsb.append(" min(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append("),");
		selectQsb.append(" avg(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append("),");
		selectQsb.append(" max(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append(")");
		
		StringBuilder qsb = new StringBuilder();
		qsb.append(selectQsb.toString());
		
		QueryComposite qc = findBeBillsByCritrSqlQuery(accountId, critr, false);
		qsb.append(" ").append(qc.getQueryString().toString());
		
		SQLQuery q = HibernateUtil.getSessionFactory().getCurrentSession().createSQLQuery(qsb.toString());
		DAOUtils.applyParameters(q, qc.getParams());
		
		Object[] qr = (Object[])q.uniqueResult();
		
		BeStats r = new BeStats();
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
