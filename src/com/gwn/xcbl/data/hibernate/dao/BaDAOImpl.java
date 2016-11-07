package com.gwn.xcbl.data.hibernate.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.gwn.xcbl.bl.bill.BillHelper;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.hibernate.dao.ba.BaLastAlertDAOImpl;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaLastAlert;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.h.AccountHqueryUtils;
import com.gwn.xcbl.data.query.h.ba.BaHqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillCableOptionsSqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillSqueryUtils;
import com.gwn.xcbl.data.shared.SortOrder;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.BillSort;
import com.gwn.xcbl.data.shared.bill.BillSortOption;

public class BaDAOImpl extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<Account> findAccountsToAlerts(LocalDateTime currentDate, Integer offset, Integer limit) {
		QueryComposite qc = findAccountsToAlertQuery(currentDate, true);
		Query q = getSession().createQuery(qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		List<Account> r = q.list();
		return r;
	}
	
	private QueryComposite findAccountsToAlertQuery(LocalDateTime currentDate, boolean orderBy) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + Account.class.getSimpleName() + " a where 1 = 1");
		AccountHqueryUtils.appendBillAlertReceiveCritr("a", qsb, params, true);
		BaHqueryUtils.appendAccountReceiveBillAlertsByDateCritr("a", qsb, params, currentDate);
		
		if (orderBy) {
			qsb.append(" ").append(new QueryOrderByBuilder().addKeyword().addOrderBy("a.id").asString());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> findLowerBills(Account account, Integer offset, Integer limit) {
		QueryComposite qc = findLowerBillsSqlQuery(account);
		SQLQuery q = getSession().createSQLQuery("select * " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		q.addEntity(Bill.class);
		List<Bill> r = q.list();
		return r;
	}
	
	private QueryComposite findLowerBillsSqlQuery(Account account) {
		Bill currentBill = DAOFactory.getInstance().getBillDAO().findCurrentBill(account.getId());
		double mileRadius = account.getBillAlertReceiveMileRadius();
		BaLastAlert lastAlert = new BaLastAlertDAOImpl().findByAccountId(account.getId());
		BigDecimal amountBelow = account.getBillAlertReceiveAmountBelow();
		BigDecimal amount = currentBill.getTotalAmount().subtract(amountBelow);
		
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BillTableMetadata.TABLE_NAME + " b where 1 = 1");
		
		BillSqueryUtils.appendAccountIdNotEqualsCritr("b", qsb, params, account.getId());
		
		BillSqueryUtils.appendZipCodeRadiusCritr("b", qsb, currentBill.getGeoZipCode().getZipCode(), mileRadius);
		if (lastAlert != null) {
			BillSqueryUtils.appendValidDateGteCritr("b", qsb, params, lastAlert.getAlertedDate().toLocalDate());
		}
		
		BillSearchCritrDTO critr = BillHelper.buildSimilarBillSearchCritr(currentBill);
		BillSqueryUtils.appendServicesCritr("b", qsb, params, critr);
		BillCableOptionsSqueryUtils.appendCableOptionCritr("b", qsb, params, critr);
		
		BillSqueryUtils.appendTotalAmountLtCritr("b", qsb, params, amount);
		
		List<BillSort> sorts = new ArrayList<BillSort>();
		sorts.add(new BillSort(BillSortOption.TOTAL_AMOUNT, SortOrder.ASC));
		sorts.add(new BillSort(BillSortOption.VALID_DATE, SortOrder.DESC));
		BillSqueryUtils.appendOrderBy("b", qsb, sorts);
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
}
