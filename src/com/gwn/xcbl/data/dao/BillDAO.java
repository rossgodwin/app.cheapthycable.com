package com.gwn.xcbl.data.dao;

import java.util.List;

import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.shared.ILongId;

public interface BillDAO extends GenericDAO<Bill, ILongId> {

	public Bill findCurrentBill(long accountId);
	
	public List<Bill> findAccountBills(long accountId, Integer offset, Integer limit);
}
