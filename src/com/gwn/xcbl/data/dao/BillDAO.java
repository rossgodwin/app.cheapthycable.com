package com.gwn.xcbl.data.dao;

import java.util.List;

import com.gwn.xcbl.data.hibernate.entity.Bill;
import com.gwn.xcbl.data.shared.ILongId;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;

public interface BillDAO extends GenericDAO<Bill, ILongId> {

	public List<Bill> findAccountBills(long accountId, Integer offset, Integer limit);
	
	public Bill findLatestBill(long accountId, boolean readOnly);
	
	public List<Bill> findBillsByCritr(BillSearchCritrDTO critr, Integer offset, Integer limit);
	
	public int countBillsByCritr(BillSearchCritrDTO critr);
}
