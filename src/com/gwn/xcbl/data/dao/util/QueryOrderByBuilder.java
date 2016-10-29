package com.gwn.xcbl.data.dao.util;

import com.gwn.xcbl.data.shared.SortOrder;

public class QueryOrderByBuilder {

	private StringBuilder result = new StringBuilder();
	
	private int orderByCount = 0;
	
	public QueryOrderByBuilder addKeyword() {
		result.append("order by");
		return this;
	}
	
	public QueryOrderByBuilder addOrderBy(String columnName) {
		addOrderBy(columnName, null);
		return this;
	}
	
	public QueryOrderByBuilder addOrderBy(String columnName, SortOrder order) {
		String orderBy = columnName;
		if (order != null && order.equals(SortOrder.DESC)) {
			orderBy += " desc";
		}
		
		if (orderByCount > 0) {
			result.append(", ");
		} else {
			if (result.length() > 0) {
				result.append(" ");
			}
		}
		
		result.append(orderBy);
		
		orderByCount++;
		
		return this;
	}
	
	public String asString() {
		return result.toString();
	}
}
