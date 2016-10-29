package com.gwn.xcbl.data.shared;

public enum SortOrder {
	ASC("Ascending"), DESC("Descending");
	
	private String label;
	
	private SortOrder(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
}
