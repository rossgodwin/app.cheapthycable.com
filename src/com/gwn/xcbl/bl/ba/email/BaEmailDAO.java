package com.gwn.xcbl.bl.ba.email;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.gwn.xcbl.bl.bill.BillHelper;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.entity.CurrentBillViewMetadata;
import com.gwn.xcbl.data.entity.ba.BaAlertTableMetadata;
import com.gwn.xcbl.data.hibernate.dao.BaseDAO;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlertSentLog;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.s.ba.BaAlertSqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillCableOptionsSqueryUtils;
import com.gwn.xcbl.data.query.s.bill.BillSqueryUtils;
import com.gwn.xcbl.data.shared.SortOrder;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.BillSort;
import com.gwn.xcbl.data.shared.bill.BillSortOption;

public class BaEmailDAO extends BaseDAO {

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
	
	/**
	 * Criteria:
	 * <ul>
	 * <li>alerts flagged to receive emails</li>
	 * <li>alerts that do not have a entry in the {@link BaAlertSentLog} table
	 * 		or alerts that do have a entry in the {@link BaAlertSentLog} table
	 * 		but the current date time is greater than the last sent date time plus
	 * 		the alert receive frequency days setting 
	 * </li>
	 * 
	 * @param currentDate
	 * @param orderBy
	 * @return
	 */
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
	
	/**
	 * Criteria:
	 * <ul>
	 * <li>current bills that are not owned by the alert account</li>
	 * <li>current bills that are within the alert radius of the current bill zip code</li>
	 * <li>current bills that have been created since the last sent alert</li>
	 * <li>current bills that are similar i.e. services and options</li>
	 * <li>current bills whose total amount is less than the current bill total amount minus the alert amount below setting</li>
	 * </ul>
	 * 	
	 * @param alert
	 * @return
	 */
	private QueryComposite findAlertBillsSqlQuery(BaAlert alert) {
		Account account = alert.getAccount();
		Bill currentBill = DAOFactory.getInstance().getBillDAO().findCurrentBill(account.getId());
		double mileRadius = alert.getCritrMileRadius();
		BaAlertSentLog lastSentAlert = DAOFactory.getInstance().getBaAlertSentLogDAO().findLastSentByAlert(alert.getId());
		BigDecimal amountBelow = alert.getCritrAmountBelow();
		BigDecimal amount = currentBill.getTotalAmount().subtract(amountBelow);
		
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("from " + BillTableMetadata.TABLE_NAME + " b, " + CurrentBillViewMetadata.TABLE_NAME + " cb");
		qsb.append(" where b." + BillTableMetadata.COL_ID + " = cb." + CurrentBillViewMetadata.COL_BILL_ID);
		
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
