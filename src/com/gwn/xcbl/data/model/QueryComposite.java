package com.gwn.xcbl.data.model;

import java.util.Map;

public class QueryComposite {

	private String queryString;
	
	private Map<String, Object> params;

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
