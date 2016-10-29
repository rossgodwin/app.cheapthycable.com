package com.gwn.xcbl.bl.bill.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.BillDAOImpl;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.entity.Bill;
import com.gwn.xcbl.data.model.HaversineFormulaCritr;
import com.gwn.xcbl.data.model.bill.BillLocationStats;
import com.gwn.xcbl.data.shared.bill.report.BillReportCritrDTO;

// TODO rename BillStatsDAOImpl
public class BillReportDAO {

	public BillLocationStats getReportData(BillReportCritrDTO critr) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("select count(1),");
		qsb.append(" count(distinct ").append(BillTableMetadata.COL_GEO_ZIP_CODE_ID).append("),");
		qsb.append(" min(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append("),");
		qsb.append(" avg(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append("),");
		qsb.append(" max(").append(BillTableMetadata.COL_TOTAL_AMOUNT).append(")");
		qsb.append(" from ").append(BillTableMetadata.TABLE_NAME).append(" as b");
		qsb.append(" where 1 = 1");
		BillDAOImpl.appendSqlZipCodeRadiusCritr("b", qsb, new HaversineFormulaCritr(critr.getLatitude(), critr.getLongitude(), critr.getRadius(), critr.getDistanceUnit()));
		BillDAOImpl.appendSqlInternetServiceCritr("b", qsb, params, critr.getInternetService());
		BillDAOImpl.appendSqlCableServiceCritr("b", qsb, params, critr.getCableService());
		BillDAOImpl.appendSqlPhoneServiceCritr("b", qsb, params, critr.getPhoneService());
		
		SQLQuery q = HibernateUtil.getSessionFactory().getCurrentSession().createSQLQuery(qsb.toString());
		DAOUtils.applyParameters(q, params);
		
		Object[] qr = (Object[])q.uniqueResult();
		
		BillLocationStats r = new BillLocationStats();
		int count = ((Number)qr[0]).intValue();
		r.setCountOfBills(count);
		if (count > 0) {
			r.setCountOfZipCodes(((Number)qr[1]).intValue());
			r.setLowestTotalAmount(roundAmount((BigDecimal)qr[2]));
			r.setAverageTotalAmount(roundAmount((BigDecimal)qr[3]));
			r.setHighestTotalAmount(roundAmount((BigDecimal)qr[4]));
		}
		
		return r;
	}
	
	// TODO can this go into a more abstract helper class ???
	private BigDecimal roundAmount(BigDecimal amount) {
		BigDecimal roundedAmount = amount.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		return roundedAmount;
	}
	
	@SuppressWarnings("unchecked")
	public List<Bill> getBillsByTotalAmount(BillReportCritrDTO critr, String totalAmount) {
		StringBuilder qsb = new StringBuilder();
		Map<String, Object> params = new HashMap<>();
		
		qsb.append("select *");
		qsb.append(" from ").append(BillTableMetadata.TABLE_NAME).append(" as b");
		qsb.append(" where 1 = 1");
		BillDAOImpl.appendSqlZipCodeRadiusCritr("b", qsb, new HaversineFormulaCritr(critr.getLatitude(), critr.getLongitude(), critr.getRadius(), critr.getDistanceUnit()));
		BillDAOImpl.appendSqlTotalAmountEqualsCritr("b", qsb, params, new BigDecimal(totalAmount));
		
		SQLQuery q = HibernateUtil.getSessionFactory().getCurrentSession().createSQLQuery(qsb.toString());
		q.addEntity(Bill.class);
		DAOUtils.applyParameters(q, params);
		
		List<Bill> r = q.list();
		return r;
	}
}
