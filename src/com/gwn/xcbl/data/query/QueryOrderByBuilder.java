package com.gwn.xcbl.data.query;

import com.gwn.xcbl.data.shared.SortOrder;

public class QueryOrderByBuilder {

	private String keyword = "order by";
	
	private StringBuilder result = new StringBuilder();
	
	private int orderByCount = 0;
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public QueryOrderByBuilder addKeyword() {
		result.append(keyword);
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
