package com.gwn.xcbl.data.shared.bill;

import com.gwn.xcbl.data.shared.SortOrder;

public class BillSort {

	private BillSortOption option;
	
	private SortOrder order;
	
	public BillSort() {
	}

	public BillSort(BillSortOption option, SortOrder order) {
		this.option = option;
		this.order = order;
	}
	
	public BillSortOption getOption() {
		return option;
	}

	public void setOption(BillSortOption option) {
		this.option = option;
	}

	public SortOrder getOrder() {
		return order;
	}

	public void setOrder(SortOrder order) {
		this.order = order;
	}
}
