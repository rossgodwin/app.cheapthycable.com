package com.gwn.xcbl.bl.bill;

import com.gwn.xcbl.data.hibernate.entity.bill.Bill;

public interface BillCreateObserver {

	public void create(Bill bill);
}
