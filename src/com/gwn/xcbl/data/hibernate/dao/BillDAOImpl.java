package com.gwn.xcbl.data.hibernate.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.gwn.xcbl.data.dao.BillDAO;
import com.gwn.xcbl.data.dao.util.QueryOrderByBuilder;
import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.entity.ProviderTableMetadata;
import com.gwn.xcbl.data.hibernate.entity.Bill;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.model.HaversineFormulaConsts;
import com.gwn.xcbl.data.model.HaversineFormulaCritr;
import com.gwn.xcbl.data.model.QueryComposite;
import com.gwn.xcbl.data.shared.ILongId;
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
		
		if (orderBy) {
			appendSqlOrderBy("b", qsb, critr.getSorts());
		}
		
		QueryComposite qc = new QueryComposite();
		qc.setQueryString(qsb.toString());
		qc.setParams(params);
		return qc;
	}
	
//	private HaversineFormulaCritr toHaversineFormulaCritr(String zipCode, Double mileRadius) {
//		List<GeoZipCode> zipCodes = DAOFactory.getInstance().getGeoZipCodeDAO().findByZipCode(zipCode);
//		
//		HaversineFormulaCritr c = new HaversineFormulaCritr();
//		c.setLatitude(zipCodes.get(0).getLatitude().doubleValue());
//		c.setLongitude(zipCodes.get(0).getLongitude().doubleValue());
//		c.setRadius(mileRadius);
//		c.setDistanceUnit(HaversineFormulaConsts.DISTANCE_UNIT_MILES);
//		
//		return c;
//	}
	
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
	
	public static void appendSqlInternetServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean service) {
		if (service != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_INTERNET_SERVICE).append(" = :").append(param);
			params.put(param, service);
		}
	}
	
	public static void appendSqlCableServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean service) {
		if (service != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_CABLE_SERVICE).append(" = :").append(param);
			params.put(param, service);
		}
	}
	
	public static void appendSqlPhoneServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean service) {
		if (service != null) {
			String param = DAOUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_PHONE_SERVICE).append(" = :").append(param);
			params.put(param, service);
		}
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
}
