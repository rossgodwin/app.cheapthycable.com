package com.gwn.xcbl.data.hibernate.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.gwn.xcbl.data.dao.BillDAO;
import com.gwn.xcbl.data.dao.util.QueryOrderByBuilder;
import com.gwn.xcbl.data.entity.BillCableOptionsTableMetadata;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.entity.ProviderTableMetadata;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.entity.Bill;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.model.HaversineFormulaConsts;
import com.gwn.xcbl.data.model.HaversineFormulaCritr;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.model.bill.BillExplorerStats;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.SetOperator;
import com.gwn.xcbl.data.shared.SortOrder;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.BillSort;
import com.gwn.xcbl.data.shared.bill.BillSortOption;

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
		AccountDAOImpl.appendIdEqualsCritr("b.account", qsb, params, accountId);
		
		if (orderBy) {
			qsb.append(" ").append(new QueryOrderByBuilder().addKeyword().addOrderBy("b.validDate", SortOrder.DESC).addOrderBy("b.id", SortOrder.DESC).asString());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
	
	public Bill findLatestBill(long accountId, boolean readOnly) {
		StringBuilder qsb = new StringBuilder();
		qsb.append("from " + Bill.class.getSimpleName() + " b0 where b0.account.id = :accountId");
		// TODO use {@link AccountDAOImpl#appendIdEqualsCritr}
		qsb.append(" and b0.id = (select max(b1.id) from " + Bill.class.getSimpleName() + " b1 where b1.account.id = :accountId)");
		
		Query q = getSession().createQuery(qsb.toString());
		q.setParameter("accountId", accountId);
		Bill r = (Bill) q.uniqueResult();
		return r;
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
		
		if (StringUtils.isNotEmpty(critr.getZipCode()) && critr.getMileRadius() != null) {
			List<GeoZipCode> zipCodes = DAOFactory.getInstance().getGeoZipCodeDAO().findByZipCode(critr.getZipCode());
			HaversineFormulaCritr c = new HaversineFormulaCritr();
			c.setLatitude(zipCodes.get(0).getLatitude().doubleValue());
			c.setLongitude(zipCodes.get(0).getLongitude().doubleValue());
			c.setRadius(critr.getMileRadius());
			c.setDistanceUnit(HaversineFormulaConsts.DISTANCE_UNIT_MILES);
			appendSqlZipCodeRadiusCritr("b", qsb, c);
		}
		appendSqlTotalAmountEqualsCritr("b", qsb, params, critr.getExactTotalAmount());
		appendSqlProviderCritr("b", qsb, params, critr.getProviderId());
		appendSqlProviderNameMatchCritr("b", qsb, params, critr.getMatchProviderName());
		appendSqlServicesCritr("b", qsb, params, critr);
		appendSqlCableOptionCritr("b", qsb, params, critr);
		
		if (orderBy) {
			appendSqlOrderBy("b", qsb, critr.getSorts());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
	
	public static void appendSqlZipCodeRadiusCritr(String tableAlias, StringBuilder qsb, HaversineFormulaCritr critr) {
		qsb.append(" and exists (");
		{
			qsb.append("select 1");
			qsb.append(" from (");
			{
				qsb.append(GeoZipCodeDAOImpl.buildHaversineTable(critr));
			}
			qsb.append(") as d");
			qsb.append(" where d.distance <= d.radius");
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_GEO_ZIP_CODE_ID).append(" = d.zip_code_id");
		}
		qsb.append(")");
	}
	
	private static void appendSqlOrderBy(String tableAlias, StringBuilder qsb, List<BillSort> sorts) {
		QueryOrderByBuilder bldr = new QueryOrderByBuilder();
		bldr.addKeyword();
		for (BillSort sort : sorts) {
			if (sort.getOption().equals(BillSortOption.VALID_DATE)) {
				bldr.addOrderBy(tableAlias + "." + BillTableMetadata.COL_VALID_DATE, sort.getOrder());
			} else if (sort.getOption().equals(BillSortOption.TOTAL_AMOUNT)) {
				bldr.addOrderBy(tableAlias + "." + BillTableMetadata.COL_TOTAL_AMOUNT, sort.getOrder());
			}
		}
		qsb.append(" ").append(bldr.asString());
	}
	
	public static void appendSqlTotalAmountEqualsCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BigDecimal amount) {
		if (amount != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_TOTAL_AMOUNT).append(" = :").append(param);
			params.put(param, amount);
		}
	}
	
	public static void appendSqlProviderCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Long providerId) {
		if (providerId != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_PROVIDER_ID).append(" = :").append(param);
			params.put(param, providerId);
		}
	}
	
	public static void appendSqlProviderNameMatchCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String providerName) {
		if (StringUtils.isNoneEmpty(providerName)) {
			qsb.append(" and (");
			{
				qsb.append("exists (");
				{
					qsb.append("select 1");
					qsb.append(" from ").append(ProviderTableMetadata.TABLE_NAME).append(" p");
					qsb.append(" where p.").append(ProviderTableMetadata.COL_ID).append(" = ").append(tableAlias).append(".").append(BillTableMetadata.COL_PROVIDER_ID);
					ProviderDAOImpl.appendSqlNameLikeCritr("p", qsb, params, providerName);
				}
				qsb.append(") or ");
				
				String param = DAOUtils.generateRandomUniqueParam(params);
				qsb.append(" lower(").append(tableAlias).append(".").append(BillTableMetadata.COL_PROVIDER_OTHER).append(") like :").append(param);
				params.put(param, "%" + providerName + "%");
			}
			qsb.append(")");
		}
	}
	
	private static void appendSqlServicesCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BillSearchCritrDTO critr) {
		if (critr.getServicesSetOperator().equals(SetOperator.CONTAINS)) {
			List<String> conds = new ArrayList<String>();
			if (critr.getInternetService() != null && critr.getInternetService()) {
				addSqlInternetServiceCritr(tableAlias, conds, params, critr.getInternetService());
			}
			if (critr.getCableService() != null && critr.getCableService()) {
				addSqlCableServiceCritr(tableAlias, conds, params, critr.getCableService());
			}
			if (critr.getPhoneService() != null && critr.getPhoneService()) {
				addSqlPhoneServiceCritr(tableAlias, conds, params, critr.getPhoneService());
			}
			if (conds.size() > 0) {
				qsb.append(" and (");
				qsb.append(StringUtils.join(conds, " or "));
				qsb.append(")");
			}
		} else if (critr.getServicesSetOperator().equals(SetOperator.MATCHES)) {
			appendSqlInternetServiceCritr(tableAlias, qsb, params, critr.getInternetService());
			appendSqlCableServiceCritr(tableAlias, qsb, params, critr.getCableService());
			appendSqlPhoneServiceCritr(tableAlias, qsb, params, critr.getPhoneService());
		}
	}
	
	public static void appendSqlInternetServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		appendSqlInternetServiceCritr(tableAlias, qsb, params, "and", value);
	}
	
	private static void appendSqlInternetServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String operator, Boolean value) {
		if (value != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			if (StringUtils.isNoneEmpty(operator)) {
				qsb.append(" ").append(operator).append(" ");
			}
			qsb.append(tableAlias).append(".").append(BillTableMetadata.COL_INTERNET_SERVICE).append(" = :").append(param);
			params.put(param, value);
		}
	}
	
	private static void addSqlInternetServiceCritr(String tableAlias, List<String> conds, Map<String, Object> params, Boolean value) {
		StringBuilder qsb = new StringBuilder();
		appendSqlInternetServiceCritr(tableAlias, qsb, params, null, value);
		String cond = qsb.toString();
		if (StringUtils.isNotEmpty(cond)) {
			conds.add(cond);
		}
	}
	
	public static void appendSqlCableServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		appendSqlCableServiceCritr(tableAlias, qsb, params, "and", value);
	}
	
	private static void appendSqlCableServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String operator, Boolean value) {
		if (value != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			if (StringUtils.isNoneEmpty(operator)) {
				qsb.append(" ").append(operator).append(" ");
			}
			qsb.append(tableAlias).append(".").append(BillTableMetadata.COL_CABLE_SERVICE).append(" = :").append(param);
			params.put(param, value);
		}
	}
	
	private static void addSqlCableServiceCritr(String tableAlias, List<String> conds, Map<String, Object> params, Boolean value) {
		StringBuilder qsb = new StringBuilder();
		appendSqlCableServiceCritr(tableAlias, qsb, params, null, value);
		String cond = qsb.toString();
		if (StringUtils.isNotEmpty(cond)) {
			conds.add(cond);
		}
	}
	
	public static void appendSqlPhoneServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		appendSqlPhoneServiceCritr(tableAlias, qsb, params, "and", value);
	}
	
	private static void appendSqlPhoneServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String operator, Boolean value) {
		if (value != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			if (StringUtils.isNoneEmpty(operator)) {
				qsb.append(" ").append(operator).append(" ");
			}
			qsb.append(tableAlias).append(".").append(BillTableMetadata.COL_PHONE_SERVICE).append(" = :").append(param);
			params.put(param, value);
		}
	}
	
	private static void addSqlPhoneServiceCritr(String tableAlias, List<String> conds, Map<String, Object> params, Boolean value) {
		StringBuilder qsb = new StringBuilder();
		appendSqlPhoneServiceCritr(tableAlias, qsb, params, null, value);
		String cond = qsb.toString();
		if (StringUtils.isNotEmpty(cond)) {
			conds.add(cond);
		}
	}
	
	private static void appendSqlCableOptionCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BillSearchCritrDTO critr) {
		if ((critr.getCableService() != null && critr.getCableService()) && (critr.getCableOptionBoxCount() != null || critr.getCableOptionDvrCount() != null || critr.getCableOptionSpecialChannels() != null)) {
			qsb.append(" and exists (");
			{
				qsb.append("select 1");
				qsb.append(" from ").append(BillCableOptionsTableMetadata.TABLE_NAME).append(" c");
				qsb.append(" where c.").append(BillCableOptionsTableMetadata.COL_BILL_ID).append(" = ").append(tableAlias).append(".").append(BillTableMetadata.COL_ID);
				BillCableOptionsDAOImpl.appendSqlBoxCountCritr("c", qsb, params, critr.getCableOptionBoxCount());
				BillCableOptionsDAOImpl.appendSqlDvrCountCritr("c", qsb, params, critr.getCableOptionDvrCount());
				BillCableOptionsDAOImpl.appendSqlSpecialChannelsCritr("c", qsb, params, critr.getCableOptionSpecialChannels());
			}
			qsb.append(")");
		}
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
}
