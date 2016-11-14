package com.gwn.xcbl.data.query.s.bill;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.gwn.xcbl.data.entity.BillTableMetadata;
import com.gwn.xcbl.data.entity.CurrentBillViewMetadata;
import com.gwn.xcbl.data.entity.ProviderTableMetadata;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.model.HaversineFormulaConsts;
import com.gwn.xcbl.data.model.HaversineFormulaCritr;
import com.gwn.xcbl.data.query.QueryOrderByBuilder;
import com.gwn.xcbl.data.query.QueryUtils;
import com.gwn.xcbl.data.query.s.GeoZipCodeSqueryUtils;
import com.gwn.xcbl.data.query.s.ProviderSqueryUtils;
import com.gwn.xcbl.data.shared.SetOperator;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.BillSort;
import com.gwn.xcbl.data.shared.bill.BillSortOption;

public class BillSqueryUtils {

	public static void appendZipCodeRadiusCritr(String tableAlias, StringBuilder qsb, String zipCode, Double mileRadius) {
		if (StringUtils.isNotEmpty(zipCode) && mileRadius != null) {
			List<GeoZipCode> zipCodes = DAOFactory.getInstance().getGeoZipCodeDAO().findByZipCode(zipCode);
			HaversineFormulaCritr c = new HaversineFormulaCritr();
			c.setLatitude(zipCodes.get(0).getLatitude().doubleValue());
			c.setLongitude(zipCodes.get(0).getLongitude().doubleValue());
			c.setRadius(mileRadius);
			c.setDistanceUnit(HaversineFormulaConsts.DISTANCE_UNIT_MILES);
			appendHaversineCritr(tableAlias, qsb, c);
		}
	}
	
	public static void appendHaversineCritr(String tableAlias, StringBuilder qsb, HaversineFormulaCritr critr) {
		qsb.append(" and exists (");
		{
			qsb.append("select 1");
			qsb.append(" from (");
			{
				qsb.append(GeoZipCodeSqueryUtils.buildHaversineTable(critr));
			}
			qsb.append(") as d");
			qsb.append(" where d.distance <= d.radius");
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_GEO_ZIP_CODE_ID).append(" = d.zip_code_id");
		}
		qsb.append(")");
	}
	
	public static void appendCurrentBillCritr(String tableAlias, StringBuilder qsb) {
		qsb.append(" and exists (");
		{
			qsb.append("select 1 from ").append(CurrentBillViewMetadata.TABLE_NAME).append(" cb");
			qsb.append(" where ").append(tableAlias).append(".").append(BillTableMetadata.COL_ID).append(" = cb.").append(CurrentBillViewMetadata.COL_BILL_ID);
		}
		qsb.append(")");
	}
	
	public static void appendAccountIdNotEqualsCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Long accountId) {
		if (accountId != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_ACCOUNT_ID).append(" != :").append(param);
			params.put(param, accountId);
		}
	}
	
	public static void appendTotalAmountEqualsCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BigDecimal amount) {
		if (amount != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_TOTAL_AMOUNT).append(" = :").append(param);
			params.put(param, amount);
		}
	}
	
	public static void appendTotalAmountLtCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BigDecimal amount) {
		if (amount != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_TOTAL_AMOUNT).append(" < :").append(param);
			params.put(param, amount);
		}
	}
	
	public static void appendProviderCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Long providerId) {
		if (providerId != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_PROVIDER_ID).append(" = :").append(param);
			params.put(param, providerId);
		}
	}
	
	public static void appendProviderNameMatchCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String providerName) {
		if (StringUtils.isNoneEmpty(providerName)) {
			qsb.append(" and (");
			{
				qsb.append("exists (");
				{
					qsb.append("select 1");
					qsb.append(" from ").append(ProviderTableMetadata.TABLE_NAME).append(" p");
					qsb.append(" where p.").append(ProviderTableMetadata.COL_ID).append(" = ").append(tableAlias).append(".").append(BillTableMetadata.COL_PROVIDER_ID);
					ProviderSqueryUtils.appendNameLikeCritr("p", qsb, params, providerName);
				}
				qsb.append(") or ");
				
				String param = QueryUtils.generateRandomUniqueParam(params);
				qsb.append(" lower(").append(tableAlias).append(".").append(BillTableMetadata.COL_PROVIDER_OTHER).append(") like :").append(param);
				params.put(param, "%" + providerName + "%");
			}
			qsb.append(")");
		}
	}
	
	public static void appendValidDateGteCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, LocalDate date) {
		if (date != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			qsb.append(" and ").append(tableAlias).append(".").append(BillTableMetadata.COL_VALID_DATE).append(" >= :").append(param);
			params.put(param, date.toString());
		}
	}
	
	public static void appendServicesCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, BillSearchCritrDTO critr) {
		if (critr.getServicesSetOperator().equals(SetOperator.CONTAINS)) {
			List<String> conds = new ArrayList<String>();
			if (critr.getInternetService() != null && critr.getInternetService()) {
				addInternetServiceCritr(tableAlias, conds, params, critr.getInternetService());
			}
			if (critr.getCableService() != null && critr.getCableService()) {
				addCableServiceCritr(tableAlias, conds, params, critr.getCableService());
			}
			if (critr.getPhoneService() != null && critr.getPhoneService()) {
				addPhoneServiceCritr(tableAlias, conds, params, critr.getPhoneService());
			}
			if (conds.size() > 0) {
				qsb.append(" and (");
				qsb.append(StringUtils.join(conds, " or "));
				qsb.append(")");
			}
		} else if (critr.getServicesSetOperator().equals(SetOperator.MATCHES)) {
			appendInternetServiceCritr(tableAlias, qsb, params, critr.getInternetService());
			appendCableServiceCritr(tableAlias, qsb, params, critr.getCableService());
			appendPhoneServiceCritr(tableAlias, qsb, params, critr.getPhoneService());
		}
	}
	
	public static void appendInternetServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		appendInternetServiceCritr(tableAlias, qsb, params, "and", value);
	}
	
	private static void appendInternetServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String operator, Boolean value) {
		if (value != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			if (StringUtils.isNoneEmpty(operator)) {
				qsb.append(" ").append(operator).append(" ");
			}
			qsb.append(tableAlias).append(".").append(BillTableMetadata.COL_INTERNET_SERVICE).append(" = :").append(param);
			params.put(param, value);
		}
	}
	
	private static void addInternetServiceCritr(String tableAlias, List<String> conds, Map<String, Object> params, Boolean value) {
		StringBuilder qsb = new StringBuilder();
		appendInternetServiceCritr(tableAlias, qsb, params, null, value);
		String cond = qsb.toString();
		if (StringUtils.isNotEmpty(cond)) {
			conds.add(cond);
		}
	}
	
	public static void appendCableServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		appendCableServiceCritr(tableAlias, qsb, params, "and", value);
	}
	
	private static void appendCableServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String operator, Boolean value) {
		if (value != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			if (StringUtils.isNoneEmpty(operator)) {
				qsb.append(" ").append(operator).append(" ");
			}
			qsb.append(tableAlias).append(".").append(BillTableMetadata.COL_CABLE_SERVICE).append(" = :").append(param);
			params.put(param, value);
		}
	}
	
	private static void addCableServiceCritr(String tableAlias, List<String> conds, Map<String, Object> params, Boolean value) {
		StringBuilder qsb = new StringBuilder();
		appendCableServiceCritr(tableAlias, qsb, params, null, value);
		String cond = qsb.toString();
		if (StringUtils.isNotEmpty(cond)) {
			conds.add(cond);
		}
	}
	
	public static void appendPhoneServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, Boolean value) {
		appendPhoneServiceCritr(tableAlias, qsb, params, "and", value);
	}
	
	private static void appendPhoneServiceCritr(String tableAlias, StringBuilder qsb, Map<String, Object> params, String operator, Boolean value) {
		if (value != null) {
			String param = QueryUtils.generateRandomUniqueParam(params);
			if (StringUtils.isNoneEmpty(operator)) {
				qsb.append(" ").append(operator).append(" ");
			}
			qsb.append(tableAlias).append(".").append(BillTableMetadata.COL_PHONE_SERVICE).append(" = :").append(param);
			params.put(param, value);
		}
	}
	
	private static void addPhoneServiceCritr(String tableAlias, List<String> conds, Map<String, Object> params, Boolean value) {
		StringBuilder qsb = new StringBuilder();
		appendPhoneServiceCritr(tableAlias, qsb, params, null, value);
		String cond = qsb.toString();
		if (StringUtils.isNotEmpty(cond)) {
			conds.add(cond);
		}
	}
	
	public static void appendOrderBy(String tableAlias, StringBuilder qsb, List<BillSort> sorts) {
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
}
