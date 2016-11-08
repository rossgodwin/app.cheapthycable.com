package com.gwn.xcbl.data.hibernate.dao.ba;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.gwn.xcbl.bl.bill.BillHelper;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlertSentLog;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.s.bill.BillCableOptionsSqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillSqueryUtils;
import com.gwn.xcbl.data.shared.SortOrder;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.BillSort;
import com.gwn.xcbl.data.shared.bill.BillSortOption;

public class BaDAOImpl extends BaseDAO {

	@SuppressWarnings("unchecked")
	public List<Bill> findAlertBills(BaAlert alert, Integer offset, Integer limit) {
		QueryComposite qc = findAlertBillsSqlQuery(alert);
		SQLQuery q = getSession().createSQLQuery("select * " + qc.getQueryString());
		DAOUtils.applyParameters(q, qc.getParams());
		DAOUtils.applyPaging(q, offset, limit);
		q.addEntity(Bill.class);
		List<Bill> r = q.list();
		return r;
	}
	
	private QueryComposite findAlertBillsSqlQuery(BaAlert alert) {
		Account account = alert.getAccount();
		Bill currentBill = DAOFactory.getInstance().getBillDAO().findCurrentBill(account.getId());
		double mileRadius = alert.getCritrMileRadius();
		BaAlertSentLog lastSentAlert = DAOFactory.getInstance().getBaAlertSentLogDAO().findLastSentByAlert(alert.getId());
		BigDecimal amountBelow = alert.getCritrAmountBelow();
		BigDecimal amount = currentBill.getTotalAmount().subtract(amountBelow);
		
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BillTableMetadata.TABLE_NAME + " b where 1 = 1");
		
		BillSqueryUtils.appendAccountIdNotEqualsCritr("b", qsb, params, account.getId());
		
		BillSqueryUtils.appendZipCodeRadiusCritr("b", qsb, currentBill.getGeoZipCode().getZipCode(), mileRadius);
		if (lastSentAlert != null) {
			BillSqueryUtils.appendValidDateGteCritr("b", qsb, params, lastSentAlert.getAlertedDate().toLocalDate());
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
