package com.gwn.xcbl.bl.bill;

import com.gwn.xcbl.data.hibernate.entity.Bill;

public interface BillCreateObserver {

	public void create(Bill bill);
}
